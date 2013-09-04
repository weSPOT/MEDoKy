<?php

elgg_register_event_handler('init', 'system', 'medoky_init');

function medoky_init() {

	elgg_extend_view('js/elgg', 'medoky/js');

}
