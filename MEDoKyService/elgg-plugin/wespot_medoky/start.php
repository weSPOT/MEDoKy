<?php
elgg_register_event_handler('init', 'system', 'medoky_init');
function medoky_init() {

elgg_register_page_handler('medoky', 'medoky_page_handler');
elgg_extend_view('page/elements/sidebar','wespot_medoky/sidebar');
}


function medoky_page_handler($segments) {
    $base_dir = elgg_get_plugins_path() . 'wespot_medoky/pages/medoky';
       include "$base_dir/main.php";
        return true;
}
