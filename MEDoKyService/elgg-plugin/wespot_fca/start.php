<?php
elgg_register_event_handler('init', 'system', 'fcatool_init');
function fcatool_init() {

	// add a site navigation item
// 	elgg_register_menu_item('site', array(
// 	'name' =>'fcatool',
// 	'text' => elgg_echo("FCA Tool"),
// 	href => "fca/main"
// 	));
elgg_register_page_handler('fca', 'fcatool_page_handler');

// add to groups
// add_group_tool_option('wespot_fca', 'FCA', true);
elgg_extend_view('groups/tool_latest', 'wespot_fca/group_module');
//elgg_register_widget_type('wespot_fca', elgg_echo('wespot_fca'), elgg_echo('wespot_fca'));
elgg_register_widget_type('wespot_fca', elgg_echo('wespot_fca:group'), elgg_echo('wespot_fca:launch'), "groups");

// add a file link to owner blocks
elgg_register_plugin_hook_handler('register', 'menu:owner_block', 'wespot_fca_sidebar');

}


function fcatool_page_handler($segments) {
    $base_dir = elgg_get_plugins_path() . 'wespot_fca/pages/fca';
    $group_guid = elgg_get_page_owner_guid();
       include "$base_dir/main.php";
        return true;

}

/**
 * Add a menu item to the user ownerblock
 */
function wespot_fca_sidebar($hook, $type, $return, $params) {
    $group = elgg_get_page_owner_entity();
    if (elgg_instanceof($params['entity'], 'user')) {

         $url='/fca/main?gid=' . $group->guid . '&name='. $group->name .'&uid=' . $group->owner_guid;
		$item = new ElggMenuItem('wespot_fca', elgg_echo('wespot_fca'), $url);
		$return[] = $item;
	} else {


        $url='/fca/main?gid=' . $group->guid . '&name='. $group->name .'&uid=' . $group->owner_guid;
        $item = new ElggMenuItem('wespot_fca', elgg_echo('wespot_fca:group'), $url);
        $return[] = $item;

	}
    file_put_contents('php://stderr', print_r($group->owner_guid, TRUE));
    file_put_contents('php://stderr', print_r('GROUP', TRUE));
	return $return;
}
