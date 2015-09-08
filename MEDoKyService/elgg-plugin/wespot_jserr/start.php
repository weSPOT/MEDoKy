<?php
elgg_register_event_handler ( 'init', 'system', 'wespot_jserr_init' );
function wespot_jserr_init() {
  elgg_extend_view('page/elements/body', 'wespot_jserr/jserr');
}
