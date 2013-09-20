<?php
/**
 * FCA widget
 */

// MB: added check for new task responses when widget draws.
// Each time the widget is drawn it calls ARLearn to check for new results.
//elgg_load_library('elgg:wespot_arlearn');
$group_guid = elgg_get_page_owner_guid();

$num = (int) $vars['entity']->wespot_fca_num;

$options = array(
	'type' => 'object',
	'subtype' => 'fca_top',
	'container_guid' => $vars['entity']->owner_guid,
	'limit' => $num,
	'full_view' => FALSE,
	'pagination' => FALSE,
);
$content = elgg_list_entities($options);
echo $content;

if ($content) {
	$url = "wespot_fca/group/" . elgg_get_page_owner_entity()->getGUID();
	$more_link = elgg_view('output/url', array(
		'href' => $url,
		'text' => 'FCA MORE',
		//'text' => elgg_echo('wespot_arlearn:more'),
		'is_trusted' => true,
	));
	echo "<span class=\"elgg-widget-more\">$more_link</span>";
} else {
	//echo elgg_echo('wespot_arlearn:none');
	echo 'FCA NONE';
}
