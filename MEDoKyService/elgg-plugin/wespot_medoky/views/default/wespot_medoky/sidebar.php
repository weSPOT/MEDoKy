<?php if(isLoggedin() && (is_group_member(elgg_get_page_owner_entity ()->guid, elgg_get_logged_in_user_guid ()))){ ?>
<?php $basedir = $CONFIG->url . "/mod/wespot_medoky/"; ?>
<link type="text/css" href="<?php echo $basedir; ?>css/smoothness/jquery-ui.css" rel="Stylesheet" />
<link type="text/css" href="<?php echo $basedir; ?>css/medoky.css" rel="Stylesheet" />

<script src="<?php echo $basedir; ?>js/jquery-create.js"></script>
<script src="<?php echo $basedir; ?>js/medoky.js"></script>
<script type="text/javascript">
$(function(){
  //if(medoky_backend.init("http://css-kmi.tugraz.at/MEDoKyService/rest/")){
  if(medoky_backend.init("http://192.168.1.101:8080/MEDoKyService/rest/")){
    medoky_ui.prepareDialogs();
    medoky.fetchRecommendations(medoky.resetView);
    setInterval(medoky.pollRecommendations, 150000);
  }
 });
</script>
<div class="elgg-module  elgg-module-aside elgg-menu-owner-block medoky_main" id="medoky_main">
  <div class="elgg-head medoky_main">
    <h3>
      <a class="pointy medoky_main" onclick="medoky_ui.showRecommendations()">Your Recommendations</a>
    </h3>
  </div>
  <ul class="elgg-menu medoky_main" id="medoky_sidebar_recommendations">
    <li id="medoky_sidebar_recommendations_LearningActivity"></li>
    <li id="medoky_sidebar_recommendations_LearningPeer"></li>
    <li id="medoky_sidebar_recommendations_LearningResource"></li>
  </ul>
</div>
<div id="dia_medoky_detail" class="medoky_main" title="Your Recommendations">
  <div id="medoky_recommmendation_detail_header" class="medoky_main"></div>
  <hr>
  <ul id="medoky_recommendation_detail_top3" class="medoky_main"></ul>
  <div id="medoky_recommendation_detail_footer" class="medoky_main"></div>
</div>
<!--
  <a href="<?php echo $CONFIG->url?>medoky">MEDOKY</a>
    -->
<?php }?>
