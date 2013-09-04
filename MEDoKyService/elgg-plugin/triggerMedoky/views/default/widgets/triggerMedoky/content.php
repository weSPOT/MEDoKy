<?php
$basedir = $CONFIG->url . "/mod/triggerMedoky/";
?>
<script>
state = {
  basedir : "",
  user: ""
};

backend = {
  url : "",
  path_trigger_dummy : "triggerRecommendationCycle/sourceId/ARLearn/userId/stefaan.ternier@gmail.com/courseId/10",
  //["triggerRecommendationCycle/sourceId/ARLearn/userId/","/courseId/10"],
  path_get_recommendation_dummy : "trigger/userId/aaculmar/courseId/gesyrt12/environment/mobile",
  // ["trigger/userId/","/courseId/gesyrt12/environment/mobile"],
  path_get_recommendation : "getRecommendation/recommendationId/",

  trigger : function(callback) {
      $("#div_trigger").empty();
        $("#div_trigger").append("Please wait...");
    $.ajax({
      cache : false,
      type : "GET",
      url : backend.url + backend.path_trigger_dummy, //[0]+state.user.username+backend.path_trigger_dummy[1],
      dataType : "json",
      contentType : "application/json; charset=utf-8",
      success : function(obj){
        $("#div_trigger").empty();
        $("#div_trigger").append(JSON.stringify(obj));
      }
    });
  },
  recommend : function(callback) {
     $("#div_recommend").empty();
       $("#div_recommend").append("Please wait...");
    $.ajax({
      cache : false,
      type : "GET",
      url : backend.url + backend.path_get_recommendation_dummy,//[0]+state.user.username+backend.path_get_recommendation_dummy[1],
      dataType : "json",
      contentType : "application/json; charset=utf-8",
      success :function(obj){
        $("#div_recommend").empty();
        $("#div_recommend").append(JSON.stringify(obj));
        backend.get_recommendation(obj);
      }
    });
  },

  get_recommendation: function(recommendation){
     $("#div_recommend").append("<br>Please wait...");
    $.ajax({
      cache : false,
      type : "GET",
      url : backend.url + backend.path_get_recommendation+recommendation.recommendationId,
      dataType : "json",
      contentType : "application/json; charset=utf-8",
      success :function(obj){
        $("#div_recommend").empty();
        console.debug(recommendation);
        $("#div_recommend").append("<br>"+(obj.recommendationText));
        $("#div_recommend").prop("style", "border: 2px solid #F00;  border-radius: 5px;");
      }
    });
  }
};

logic = {
  init : function(basedir, backend_url) {
    state.basedir = basedir;
    state.user = elgg.get_logged_in_user_entity();

    backend.url = backend_url;
  }
};



$(function(){
  logic.init("<?php echo $basedir; ?>","http://css-kmi.tugraz.at:8080/MEDoKyService/rest/");
});
</script>

<!--<input type="button" value="Trigger" onclick="backend.trigger()"></input>-->
<input type="button" value="Get Recommendation" onclick="backend.recommend()"></input>
<div id="div_trigger"></div>
<div id="div_recommend"></div>
