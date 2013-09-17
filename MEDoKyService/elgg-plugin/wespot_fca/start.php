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

}

 
function fcatool_page_handler($segments) {
    $base_dir = elgg_get_plugins_path() . 'wespot_fca/pages/fca';
       include "$base_dir/main.php";
        return true;
 
}
