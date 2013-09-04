<?php
$basedir = $CONFIG->url . "/mod/triggerMedoky/";
?>
<script type="text/javascript" src="<?php echo $basedir; ?>js/triggerMedoky.js"></script>
<script>
$(function(){
logic.init("<?php echo $basedir; ?>","http://css-kmi.tugraz.at:8081/MEDoKyService/rest/");
});
</script>
<div>
Back-end Path: http://css-kmi.tugraz.at:8080/MEDoKyService/rest/
</div>
<input type="button" value="Trigger" onclick="backend.trigger()"></input>
<input type="button" value="Recommend" onclick="backend.recommend()"></input>
<div id="div_trigger"></div>
<div id="div_recommend"></div>
