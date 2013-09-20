<?php
elgg_register_event_handler('init', 'system', 'fcatool_init');
function fcatool_init() {

	// add a site navigation item
	elgg_register_menu_item('site', array(
	'name' =>'fcatool',
	'text' => elgg_echo("FCA Tool"),
	href => "fca/main"
	));
elgg_register_page_handler('fca', 'fcatool_page_handler');

// add to groups
add_group_tool_option('wespot_fca', 'FCA', true);
elgg_extend_view('groups/tool_latest', 'wespot_fca/group_module');
//elgg_register_widget_type('wespot_fca', elgg_echo('wespot_arlearn'), elgg_echo('wespot_arlearn:widget:description'), "groups");
elgg_register_widget_type('wespot_fca', elgg_echo('wespot_fca'), "FCA WIDGET", "groups");

}

 
function fcatool_page_handler($segments) {
    $base_dir = elgg_get_plugins_path() . 'wespot_fca/pages/fca';
    $group_guid = elgg_get_page_owner_guid();
       include "$base_dir/main.php";
        return true;
 
}
