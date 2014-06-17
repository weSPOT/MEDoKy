function MEDoKyRecommendation(type, text, link, linkTitle, description, id) {
  this.getIcon = function() {
    if (type == MEDoKyRecommendation.TYPE_ACTIVITY)
      return "img/activity.svg";
    else if (type == MEDoKyRecommendation.TYPE_PEER)
      return "img/peer.svg";
    else if (type == MEDoKyRecommendation.TYPE_RESOURCE)
      return "img/resource.svg";
  };

  this.getType = function() {
    return type;
  };

  this.getId = function() {
    return id;
  };

  this.getText = function() {
    return text;
  };

  this.getDescription = function() {
    return description;
  };

  this.getLink = function() {
    return link;
  };

  this.getLinkTitle = function() {
    return linkTitle;
  };
}
MEDoKyRecommendation.TYPE_ACTIVITY = "LearningActivity";
MEDoKyRecommendation.TYPE_PEER = "LearningPeer";
MEDoKyRecommendation.TYPE_RESOURCE = "LearningResource";

var medoky_recommendation_state = {
  user : elgg.get_logged_in_user_entity(),
  gid : elgg.get_page_owner_guid(),
  basedir : elgg.config.wwwroot + "/mod/wespot_medoky/",
  recommendations : [],
  next_recommendations : [],
  lock : function(id) {
    medoky_recommendation_state.id_detail = id;
    medoky_recommendation_state.locked = true;
  },
  unlock : function() {
    medoky_recommendation_state.locked = false;
  }

};

var medoky_ui = {
  prepareDialogs : function() {
    if(!medoky_recommendation_state.gid)
    return;
    $("#dia_medoky_detail").dialog({
      autoOpen : false,
      height : 350,
      width : 600,
      resizable : false,
      modal : true
    });
  },

  showRecommendations : function() {
    var header = $("#medoky_recommmendation_detail_header").empty();
    var ul = $("#medoky_recommendation_detail_top3").empty();
    var footer = $("#medoky_recommendation_detail_footer").empty();

    header
        .create(
            "txt",
            "Hello "
                + medoky_recommendation_state.user.name
                + ", take a look at your personalised learning recommendations. You may find it useful ...");

    for ( var i in medoky_recommendation_state.recommendations) {
      var recommendation = medoky_recommendation_state.recommendations[i];
      medoky_ui.addRecommendationDetail(ul, recommendation, false);
    }
    footer.create("hr");
    footer.create("a", {
      class : "pointy bold"
    }).create("txt", "Here");
    footer
        .create("txt",
            " you can find an overview of recommendations that interested you in the past.");
    try{
      //initial test
      post_to_stepup(window.location.href, "view recommendations", {course : gid}, "dummy");
    }catch(error){
      console.log(error);
    }

    $("#dia_medoky_detail").dialog("open");
  },

  showRecommendationDetail : function(id) {
    if (medoky_recommendation_state.locked)
      return;
    if (!(medoky_recommendation_state.id_detail === undefined))
      $("#medoky_recommendation_id_" + medoky_recommendation_state.id_detail)
          .slideUp(300, function() {
            medoky_recommendation_state.unlock();
          });
    if (!(medoky_recommendation_state.id_detail === id)) {
      $("#medoky_recommendation_id_" + id).slideDown(300, function() {
        medoky_recommendation_state.unlock();
      });
      medoky_recommendation_state.lock(id);
    } else {
      setTimeout(function() {
        medoky_recommendation_state.id_detail = undefined;
      }, 300);
    }
  },

  addRecommendationDetail : function(ul, recommendation, animate) {
    var li = ul.create("li", {
      class : "rec",
      id : "medoky_recommendation_detail_id_" + recommendation.getId()
    });
    if (animate) {
      li.slideUp(0);
    }

    var span = li.create("span", {
      class : "medoky_recommendation_title pointy",
      onclick : "medoky_ui.showRecommendationDetail(" + recommendation.getId()
          + ")"
    });
    span.create("img", {
      src : medoky_recommendation_state.basedir + recommendation.getIcon(),
      width : "22px",
      height : "22px",
      class : "medoky_recommendation"
    });
    span.create("txt", recommendation.getText());

    var span_rating = li.create("span", {
      id : "medoky_recommendation_rating_" + recommendation.getId()
    });
    span_rating.create("img", {
      class : "medoky_star pointy",
      src : medoky_recommendation_state.basedir + "img/star.svg",
      width : "22px",
      height : "22px",
      onclick : " $(\"#medoky_recommendation_rating_" + recommendation.getId()
          + "\").data(\"rating\", 1);"
    });
    span_rating.create("img", {
      class : "medoky_star pointy",
      src : medoky_recommendation_state.basedir + "img/star.svg",
      width : "22px",
      height : "22px",
      onclick : " $(\"#medoky_recommendation_rating_" + recommendation.getId()
          + "\").data(\"rating\", 2);"
    });
    span_rating.create("img", {
      class : "medoky_star pointy",
      src : medoky_recommendation_state.basedir + "img/star.svg",
      width : "22px",
      height : "22px",
      onclick : " $(\"#medoky_recommendation_rating_" + recommendation.getId()
          + "\").data(\"rating\", 3);"
    });
    span_rating.create("img", {
      class : "medoky_star pointy",
      src : medoky_recommendation_state.basedir + "img/star.svg",
      width : "22px",
      height : "22px",
      onclick : " $(\"#medoky_recommendation_rating_" + recommendation.getId()
          + "\").data(\"rating\", 4);"
    });
    span_rating.create("img", {
      class : "medoky_star pointy",
      src : medoky_recommendation_state.basedir + "img/star.svg",
      width : "22px",
      height : "22px",
      onclick : " $(\"#medoky_recommendation_rating_" + recommendation.getId()
          + "\").data(\"rating\", 5);"
    });
    span_rating.mousemove(function(evt) {
      var posX = evt.pageX - $(this).offset().left;
      var rating = Math.floor(posX / $(this).width() * 5);
      for (var i = 0; i < this.childNodes.length; ++i) {
        if (i <= rating)
          $(this.childNodes[i]).attr("src",
              medoky_recommendation_state.basedir + "img/star_active.svg");
        else
          $(this.childNodes[i]).attr("src",
              medoky_recommendation_state.basedir + "img/star.svg");
      }
    });
    span_rating.hover(function(evt) {
      // well, nothing actually
    }, function(evt) {

      for (var i = 0; i < 5; ++i) {
        if (i < $(this).data("rating"))
          $(this.childNodes[i]).attr("src",
              medoky_recommendation_state.basedir + "img/star_active.svg");
        else
          $(this.childNodes[i]).attr("src",
              medoky_recommendation_state.basedir + "img/star.svg");
      }
    });

    li.create("img", {
      class : "medoky_star pointy medoky_recommendation_ok",
      src : medoky_recommendation_state.basedir + "img/ok.svg",
      width : "22px",
      height : "22px",
      onclick : "medoky_util.confirmRecommendation(" + recommendation.getId()
          + ")"
    });
    var detail = li.create("div", {
      id : "medoky_recommendation_id_" + recommendation.getId(),
      class : "medoky_recommendation_detail"
    }).hide();

    var recommendedResource = recommendation.getLink();
    if (recommendedResource) {
      detail.create("txt", "Recommended Resource: ");
      detail.create(
          "a",
          {
            class : "pointy bold",
            onclick : "window.open(\"" + recommendation.getLink()
                + "\", \"Learning Object\", \"width=800,height=600\")"
          }).create("txt", recommendation.getLinkTitle());
      detail.create("br");
    }

    detail.create("txt", recommendation.getDescription());
    if (animate) {
      li.slideDown(300);
    }
  }
};

var medoky_backend = {

  init : function(backend_url) {
    if(!medoky_recommendation_state.gid)
    return;
    else
    $(".medoky_main").removeClass("medoky_main");
    medoky_backend.url = backend_url;
  },

  url : "", // set in init function
  path_trigger : "trigger/userId/#/courseId/#/number/#/environment/#",
  path_getrecommendation : "getRecommendation/recommendationId/",

  trigger : function(uid, callback, cid, num, environ) {
    cid = cid ? cid : 1;
    num = num ? num : 10;
    environ = environ ? environ : "textBased";

    var path_trigger_parts = medoky_backend.path_trigger.split("#");
    var path_trigger = path_trigger_parts[0] + uid + path_trigger_parts[1]
        + cid + path_trigger_parts[2] + num + path_trigger_parts[3] + environ;
    $.ajax({
      cache : false,
      type : "GET",
      url : medoky_backend.url + path_trigger,
      success : function(obj) {
        if (callback)
          callback(obj.recommendationId);
      },
      error : function(obj) {
        console.error(JSON.stringify(obj));
      }
    });
  },

  getRecommentations : function(rid, callback) {
    $.ajax({
      cache : false,
      type : "GET",
      url : medoky_backend.url + medoky_backend.path_getrecommendation + rid,
      success : function(obj) {
        if (callback) {
          var recommendations = [];
          for ( var i in obj.recommendations) {
            var rec = obj.recommendations[i];
            recommendations.push(medoky_util.createRecommendation(rec.type,
                rec.recommendationText, rec.link, rec.linkTitle, rec.explanation));
          }
          callback(recommendations);
        }
      },
      error : function(obj) {
        console.error(JSON.stringify(obj));
      }
    });
  }

};

var medoky = {
  displayRecommendation : function(id) {
    medoky_ui.showRecommendations();
    medoky_ui.showRecommendationDetail(id);
  },

  getRecommendations : function(callback) {
    if(!medoky_recommendation_state.gid)
    return;
    medoky_backend
        .trigger(
            medoky_recommendation_state.user.id,
            function(recs_id) {
              medoky_backend
                  .getRecommentations(
                      recs_id,
                      function(recommendations) {
                        medoky_recommendation_state.next_recommendations = medoky_recommendation_state.next_recommendations
                            .concat(recommendations);
                        if (callback)
                          callback();
                      });
            }, medoky_recommendation_state.gid, 10);

  },

  displayInitialRecommendations : function() {
    medoky_util.queueRecommendation();
    medoky_util.queueRecommendation();
    medoky_util.queueRecommendation();
  }
};

var medoky_util = {
  lastId : -90000000,

  createRecommendation : function(type, text, link, linkTitle, description) {
    var id = ++(medoky_util.lastId);
    return new MEDoKyRecommendation(type, text, link, linkTitle, description, id);
  },

  queueRecommendation : function(id) {
    if (id)
      medoky_recommendation_state.recommendations.splice(id, 1);

    var newRec = medoky_recommendation_state.next_recommendations.splice(0, 1)[0];
    console.debug(newRec);
    medoky_recommendation_state.recommendations[newRec.getId()] = newRec;
    if (medoky_recommendation_state.next_recommendations.length < 2)
      medoky.getRecommendations();

    var rec = $("#medoky_sidebar_recommendations").create("li", {
      id : "medoky_sidebar_recommendation_id_" + newRec.getId()
    }).create("a", {
      class : "pointy",
      onclick : "medoky.displayRecommendation(" + newRec.getId() + ")"
    }).slideUp(0);
    rec.create("img", {
      src : medoky_recommendation_state.basedir + newRec.getIcon(),
      width : "22px",
      height : "22px",
      class : "medoky_recommendation"
    });
    rec.create("txt", newRec.getText());
    rec.slideDown(300);
    return newRec.getId();
  },

  confirmRecommendation : function(id) {
    if (medoky_recommendation_state.animating)
      return;
    medoky_recommendation_state.animating = true;
    console.debug(id);
    $(
        "#medoky_recommendation_detail_id_" + id
            + ", #medoky_sidebar_recommendation_id_" + id).prop("disabled",
        true);
    $(
        "#medoky_recommendation_detail_id_" + id
            + ", #medoky_sidebar_recommendation_id_" + id).slideUp(
        300,
        function() {
          $(
              "#medoky_recommendation_detail_id_" + id
                  + ", #medoky_sidebar_recommendation_id_" + id).remove();
          medoky_recommendation_state.animating = false;
        });

    /*
     *
     * medoky_recommendation_state.recommendations.splice(id, 1); var newId =
     * Math.ceil(id + Math.random() * 99); while (newId in
     * medoky_recommendation_state.recommendations) { newId = Math.ceil(id +
     * Math.random() * 99); } console.debug(newId);
     * medoky_util.createRecommendation(MEDoKyRecommendation.TYPE_PEER, "Peer
     * Recommendation " + newId, "", "Lorem ipsum,...", newId);
     *
     */
    var newId = medoky_util.queueRecommendation(id);
    medoky_ui.addRecommendationDetail($("#medoky_recommendation_detail_top3"),
        medoky_recommendation_state.recommendations[newId], true);
  }
};
