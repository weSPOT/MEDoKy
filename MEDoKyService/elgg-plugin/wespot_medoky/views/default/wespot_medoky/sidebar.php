<?php if(isLoggedin()){ ?>
<?php $basedir = $CONFIG->url . "/mod/wespot_medoky/"; ?>
<link type="text/css" href="<?php echo $basedir; ?>css/smoothness/jquery-ui.css" rel="Stylesheet" />
<link type="text/css" href="<?php echo $basedir; ?>css/medoky.css" rel="Stylesheet" />

<script src="<?php echo $basedir; ?>js/jquery-create.js"></script>
<script src="<?php echo $basedir; ?>js/medoky.js"></script>
<script type="text/javascript">
$(function(){
  //medoky_backend.init("http://192.168.1.1:8080/MEDoKyService/rest/");
  medoky_backend.init("http://css-kti.tugraz.at/MEDoKyService/rest/");
  medoky_ui.prepareDialogs();
  medoky.getRecommendations(medoky.displayInitialRecommendations);
 });
</script>
<div class="elgg-module  elgg-module-aside elgg-menu-owner-block">
  <div class="elgg-head">
    <h3>
      <a class="pointy" onclick="medoky_ui.showRecommendations()">Your Recommendations</a>
    </h3>
  </div>
  <ul class="elgg-menu" id="medoky_sidebar_recommendations">
  </ul>
</div>
<div id="dia_medoky_detail" title="Your Recommendations">
  <div id="medoky_recommmendation_detail_header"></div>
  <hr>
  <ul id="medoky_recommendation_detail_top3"></ul>
  <div id="medoky_recommendation_detail_footer"></div>
</div>
<!--
  <a href="<?php echo $CONFIG->url?>medoky">MEDOKY</a>
    -->
<?php }?>
