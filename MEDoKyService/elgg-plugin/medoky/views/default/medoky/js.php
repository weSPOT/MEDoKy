elgg.provide('elgg.medoky');
elgg.medoky= {

  init: function() {

  },

  print: function(str){
        console.debug("MEDoKy says: "+str);
  },

  get_learning_objects : function(callback) {
    $.ajax({
      cache : false,
      type : "GET",
      url :"http://css-kmi.tugraz.at:8081/MEDoKyService/rest/FCATool/getLearningObjects",
      success : function(obj) {
          for(var o in obj){
            obj[o].data=obj[o].description;
            obj[o].description="";
          }
          callback(obj);
      },
      error : function(obj) {
        console.error(JSON.stringify(obj));
      }
    });
  },

  create_learning_objects : function(l_objects, callback) {
    //this is ugly
    for(var o in l_objects){
      l_objects[o].description = l_objects[o].data;
      l_objects[o].data = "";
      l_objects[o].externalUID = elgg.get_logged_in_user_guid();
    }
    $.ajax({
      cache : false,
      type : "POST",
      url : "http://css-kmi.tugraz.at:8081/MEDoKyService/rest/FCATool/createLearningObjects",
      data : JSON.stringify(l_objects),
      dataType : "json",
      contentType : "application/json; charset=utf-8",
      success : function(obj){
          for(var o in obj){
            obj[o].data=obj[o].description;
            obj[o].description="";
          }
        callback(obj);
      }
    });
  }
}

elgg.register_hook_handler('init', 'system', elgg.medoky.init);
