<?php
/**
 * Elgg Pages
 *
 * @package ElggPages
 */

// Load the httpful REST library
if (!include_once(dirname(__FILE__) . "/httpful.phar")) {
    $msg = 'Elgg could not load the httpful library. It does not exist or there is a file permissions issue.';
    throw new InstallationException($msg);
}

elgg_register_event_handler('init', 'system', 'stepup_init');

/**
 * Initialize the stepup plugin.
 *
 */
function stepup_init() {
    $url = elgg_get_simplecache_url('js', 'wespot_stepup/api');
    elgg_register_simplecache_view('js/wespot_stepup/api');
    elgg_register_js('wespot_stepup', $url);

    $js_rating = elgg_get_simplecache_url('js', 'elggx_fivestar/ui.stars.min');
    elgg_register_simplecache_view('js/elggx_fivestar/ui.stars.min');
    elgg_register_js('fivestar', $js_rating);


    //HOW TO USE in other plugins:
    //PHP: elgg_load_js('wespot_stepup');
    //JS: post_to_stepup("url", "verb", "context", "value")

    # http://docs.elgg.org/wiki/What_events_are_available_in_the_Elgg_core%3F
    elgg_register_event_handler('created', 'river', 'river_create');
    elgg_register_event_handler('create', 'object', 'create_object');
    elgg_register_event_handler('create', 'group', 'create_inquiry');
    elgg_register_event_handler('create', 'answer', 'create_answer');
    elgg_register_event_handler('update', 'object', 'update_object');
    #elgg_register_event_handler('delete', 'object', 'delete_object');
    elgg_register_event_handler('create', 'annotation', '_create_annotation');
    elgg_register_event_handler('delete', 'annotation_from_ui', 'annotation_from_ui');

    elgg_register_event_handler('all', 'rating', 'rating');

    expose_function("stepup.proxy",
        "proxy",
        null,
        'Proxy for JavaScript to use to avoid Cross-Origin Request problems',
        'POST',
        false,
        false
    );

    expose_function("stepup.updatedb",
        "updatedb",
        null,
        '',
        'GET',
        false,
        false
    );
}


// http://localhost/elgg/services/api/rest/json/?method=stepup.updatedb
function updatedb() {

    //$res = get_metastring_id('tab');

    $options = array(
        'metadata_names' => 'tab',
        'limit' => 0
    );

    $entities = elgg_get_entities_from_metadata($options);
    foreach($entities as $entity) {
        $entity->phase = get_entity($entity->tab)->order;
    }

    return "Updated " . count($entities) . " objects";
}

function proxy() {
    $body = $_POST;
    $body['username'] = get_uid();
    $time = date('Y-m-d H:i:s O', time());
    $body['starttime'] = $time;
    $body['endtime'] = $time;
    $result = post_body($body);
    if($result->body->status == "200") {
       return "ok";
    } else {
       return "error";
    }
}

function get_uid() {
    return elgg_get_plugin_user_setting('uid', elgg_get_logged_in_user_guid(), 'elgg_social_login');
}

// post data to the StepUp service
function post_data($url, $verb, $context, $value = null) {

    if (strpos($url, '#') !== false) { # /hypothesis/433#annotate-125
        #$url = explode('#', $url)[0];
        $parts = explode('#', $url);
        $url = $parts[0];
    }
    $time = date('Y-m-d H:i:s O', time());

    $context['phase'] = (string)$context['phase'];

    $user = get_uid();
    if(!$user) {
        $user = "Unknown";
    }

    $body_array = array(
        'username' => $user,
        'verb' => $verb,
        'object' => $url,
        'starttime' => $time,
        'endtime' => $time,
        'context' => $context
    );
    if(isset($value)) {
        $body_array['originalrequest'] = array('value' => $value);
    }

    $body = json_encode($body_array);

    unset($body_array['starttime']);
    unset($body_array['endtime']);

    # we have to apply a fix for Elgg core when an event can be triggered twice or more due to bugs
    # we save a few last request signatures and times and check if the same request happened within last 5s
    $duplicate = false;
    $last_requests = null;
    if (array_key_exists('wespot_last_requests', $_SESSION)) {
        $last_requests = $_SESSION['wespot_last_requests'];
    } else {
        $last_requests = array();
    }
    $sha = sha1(json_encode($body_array)); # array with no times
    $time = time();

    foreach ($last_requests as $request) {
        if($request[1] > $time - 5 && $request[0] == $sha) { # if same request happened in last 5s, we have a duplicate
            $duplicate = true;
            break;
        }
    }

    array_push($last_requests, array($sha, $time));
    if(count($last_requests) > 15) {
        array_shift($last_requests);
    }
    $_SESSION['wespot_last_requests'] = $last_requests;

    if(!$duplicate) {
        //error_log("POST BODY".$body);
        $result = post_body($body);
        //error_log("POST RESULT".$result);
    }
}

function post_body($body) {
    //$endpoint = "http://requestb.in/q74pyaq7";
    $endpoint = "http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent";
    $result = \Httpful\Request::post($endpoint)
        ->sendsJson()
        ->body($body)
        ->send();
    $endpoint = "http://css-kmi.tugraz.at/weSpotLogServer/rest/v1/contentData/add";
    $result = \Httpful\Request::post($endpoint)
        ->sendsJson()
        ->body($body)
        ->send();
    return $result;
}

function create_inquiry($event, $type, $inquiry) {
   curl_post("http://openbadges-wespot.appspot.com/rest/badges/wespot/" . $inquiry->guid, 'i6vqbac9calj4cl689maq1ius3');
}

function curl_post($url, $token)
{
    $ch = curl_init($url);

    $headers = array('Content-Type: application/json',
                     'Authorization: ' . $token,
                     'Content-Length: 0');

    curl_setopt($ch, CURLOPT_POST, true);
    #curl_setopt($ch, CURLOPT_VERBOSE, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_TIMEOUT, 7);

    curl_exec($ch);
    curl_close($ch);
}

function plugin_from_subtype($handle) {
    return str_replace("_top", "", $handle);
}

function is_standard_plugin($plugin) {
    $standard_plugins = array("hypothesis", "notes", "reflection", "conclusions", "page", "question", "groupforumtopic", "mindmeistermap", "arlearntask", "file");
    return in_array($plugin, $standard_plugins);
}

function get_phase($plugin, $entity) {
    $phase = get_phase_for_plugin($plugin);

    if($entity->getSubtype() == 'answer') {
        $rel = get_entity_relationships($entity->guid, true);
        $question = get_entity($rel[0]->guid_one);
        if($question->phase) {
            $phase = $question->phase;
        }
    } elseif($entity->phase) {
        $phase = $entity->phase;
    }

    return $phase;
}

function get_phase_for_plugin($plugin) {
    switch ($plugin) {
        case "hypothesis":
            return 1;
        case "question":
            return 1;
        case "answer":
            return 1;
        case "mindmeistermap":
            return 1;
        case "notes":
            return 2;
        case "page":
            return 2;
        case "arlearntask":
            return 3;
        case "file":
            return 4;
        case "groupforumtopic":
            return 5;
        case "reflection":
            return 6;
        case "conclusions":
            return 6;
    }
}

// send data to stepup service
function river_create($event, $type, $river_item) {
    $object = get_entity($river_item->object_guid);

    if ($object->getSubtype() == 'izap_challenge_results') {

        $quiz = get_entity($object->container_guid);
        $url = $quiz->getURL();
        $result = $object->total_percentage;

        switch ($object->container_guid) {
        	case 4364:
        		$phase_id = 1;
        		break;
        	case 4401:
        		$phase_id = 2;
        		break;
        	case 4409:
        		$phase_id = 3;
        		break;
        	case 4426:
        		$phase_id = 4;
        		break;
        	case 4433:
        		$phase_id = 5;
        		break;
        	case 4441:
        		$phase_id = 6;
        		break;
		}

        $context = array("course" => "", "phase" => $phase_id, "subphase" => "assessment");

        post_data($url, "answer", $context, $result);
    }
}


function create_object($event, $type, $object) {

    $plugin = plugin_from_subtype($object->getSubtype());

    if (is_standard_plugin($plugin)) {
        $url = $object->getURL();
        $inquiry_id = $object->container_guid;

        $context = array("course" => $inquiry_id, "phase" => get_phase($plugin, $object), "subphase" => $plugin);
        $value = array('title' => $object->title, 'description' => $object->description, 'id' => $object->guid);

        if($plugin == 'hypothesis' || $plugin == 'question' || $plugin == 'groupforumtopic' || $plugin == 'conclusions' || $plugin == 'reflection' ||
            $plugin == 'notes' || $plugin == 'page' || $plugin == 'arlearntask' || $plugin == 'file') {
            $value['tags'] = $object->tags;
        }

        if($plugin == 'file') {
            $value['access'] = $object->access_id;
        }

        if($plugin == 'arlearntask') {
            $value['type'] = $_POST['task_type'];
        }

        post_data($url, 'create', $context, $value);
    }
}

# we catch relationship creation instead of answer object creation so that we can easily get to the question the answer belongs to
function create_answer($event, $type, $relationship) {

    $question = get_entity($relationship->guid_one);
    $answer = get_entity($relationship->guid_two);

    if ($answer->getSubtype() == 'answer') {
        $url = $answer->getURL();
        $inquiry_id = $answer->container_guid;
        $context = array("course" => $inquiry_id, "phase" => get_phase('question', $question), "subphase" => 'question');
        $value = array('description' => $answer->description, 'question_id' => $question->guid);

        #if($plugin == 'hypothesis' || $plugin == 'question' || $plugin == 'groupforumtopic') {
        #    $value['tags'] = $object->tags;
        #}

        post_data($url, 'answer', $context, $value);
    }
}


function update_object($event, $type, $object) {

    $plugin = plugin_from_subtype($object->getSubtype());

    if (is_standard_plugin($plugin)) {
        $url = $object->getURL();
        $inquiry_id = $object->container_guid;

        $context = array("course" => $inquiry_id, "phase" => get_phase($plugin, $object), "subphase" => $plugin);
        $value = array('title' => $object->title, 'description' => $object->description, 'id' => $object->guid);

        if($plugin == 'hypothesis' || $plugin == 'question' || $plugin == 'groupforumtopic' || $plugin == 'conclusions' || $plugin == 'reflection' ||
            $plugin == 'notes' || $plugin == 'page' || $plugin == 'arlearntask' || $plugin == 'file') {
            $value['tags'] = $object->tags;
        }

        if($plugin == 'file') {
            $value['access'] = $object->access_id;
        }

        if($plugin == 'arlearntask') {
            $value['type'] = $_POST['task_type'];
        }

        if($plugin == 'groupforumtopic') {
            $value['discussion_id'] = $object->container_guid;
        }

        post_data($url, 'edit', $context, $value);
    }
}


function delete_object($event, $type, $object) {

    $plugin = plugin_from_subtype($object->getSubtype());

    if (is_standard_plugin($plugin)) {
        $url = $object->getURL();
        $inquiry_id = $object->container_guid;
        $context = array("course" => $inquiry_id, "phase" => get_phase($plugin, $object), "subphase" => $plugin);

        post_data($url, 'delete', $context);
    }
}


function _create_annotation($event, $type, $annotation) {
    $subtype = $annotation->getSubtype();

    if($subtype == "generic_comment" || $subtype == "likes" || $subtype == "group_topic_post") # error: Call to a member function getURL() on a non-object in ARLearn data collection task
    {
        $entity = get_entity($annotation->entity_guid);
        $url = $entity->getURL();
        $inquiry_id = $entity->container_guid;
        $plugin = plugin_from_subtype($entity->getSubtype());

        if (is_standard_plugin($plugin) || $plugin == 'answer') {

            $phase = get_phase($plugin, $entity);

            $context = array("course" => $inquiry_id, "phase" => $phase, "subphase" => $plugin);

            switch ($subtype)
            {
                case "generic_comment":
                    $value = array('id' => $entity->guid, 'comment_id' => $annotation->id, 'description' => $annotation->value);
                    post_data($url, 'comment', $context, $value);
                    break;
                case "likes":
                    $value = array('id' => $entity->guid);
                    post_data($url, 'like', $context, $value);
                    break;
                case "group_topic_post":
                    $value = array('description' => $annotation->value, 'reply_id' => $annotation->id, 'id' => $entity->guid);
                    post_data($url, 'reply', $context, $value);
                    break;
            }
        }
    }
}


function annotation_from_ui($event, $type, $annotation) {
    if($event == 'delete')
    {
        $url = "";
        $entity = null;
        if(get_class($annotation) == 'ElggObject') { # received when deleting question or answer
            $entity = $annotation;
            $url = $entity->getURL();
        } elseif(get_class($annotation) == 'FilePluginFile') { # received when deleting question or answer
            $entity = $annotation;
        } else  {
            $entity = get_entity($annotation->entity_guid);
            $url = $entity->getURL();
        }

        $inquiry_id = $entity->container_guid;
        $plugin = plugin_from_subtype($entity->getSubtype());

        if (is_standard_plugin($plugin) || $plugin == 'answer') {

            $context = array("course" => $inquiry_id, "phase" => get_phase($plugin, $entity), "subphase" => $plugin);
            $value = array('id' => $entity->guid);

            switch ($annotation->getSubtype())
            {
                case "generic_comment":
                    post_data($url, 'delete_comment', $context, $value);
                    break;
                case "file":
                    post_data($url, 'delete_file', $context, $value);
                    break;
                case "likes":
                    post_data($url, 'delete_like', $context, $value);
                    break;
                case "group_topic_post":
                    post_data($url, 'delete_discussion_topic_reply', $context, $value);
                    break;
                case "question":
                    post_data($url, 'delete_question', $context, $value);
                    break;
                case "answer":
                    post_data($url, 'delete_answer', $context, $value);
                    break;
                case "page_top":
                    post_data($url, 'delete_page', $context, $value);
                    break;
                case "mindmeistermap":
                    post_data($url, 'delete_mindmeistermap', $context, $value);
                    break;
                case "groupforumtopic":
                    post_data($url, 'delete_topic', $context, $value);
                    break;
                case "arlearntask_top":
                    post_data($url, 'delete_arlearntask', $context, $value);
                    break;
            }
        }
    }
}


function rating($event, $type, $rating) {
    //error_log("RATING handler called!");
    $entity = get_entity($rating->entity_guid);
    $url = $entity->getURL();
    $inquiry_id = $entity->container_guid;
    $plugin = plugin_from_subtype($entity->getSubtype());

    if (is_standard_plugin($plugin)) {
        $context = array("course" => $inquiry_id, "phase" => get_phase($plugin, $entity), "subphase" => $plugin);
        $value = $rating->value/20;

        switch ($event)
        {
            case "rate":
                #kill( $context ) { die( var_dump ( $context ) ); }
                #var_dump($context);
                //error_log("RATING sent!".print_r($context, true));
                post_data($url, 'rated', $context, $value);
                break;
            #case "update": # disable that because "rate" is sent upon updating rating anyway (so two events are sent in that case)
            #    post_data($url, 'rating_updated', $context, $value);
            #    break;
        }

    }
}