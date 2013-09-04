<?php
elgg_register_event_handler('init', 'system', 'triggerMedoky_init');
function triggerMedoky_init() {

	elgg_register_widget_type('triggerMedoky', 'MEDoKy Tester', 'MEDoKy Tester');

}
