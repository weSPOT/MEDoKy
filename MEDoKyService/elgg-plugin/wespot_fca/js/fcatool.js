state = {
  basedir : "",
  user : {},
  id_last_attr : 3,
  id_last_obj : 4,
  backend_objects : {},
  backend_attributes : {},
  backend_l_objects : {},
  new_objects : {},
  new_attributes : {},
  active_l_objects : {},
  obj_index : -1,
  attr_index : -1,
  domain : [],
  inited_attr : false,
  inited_obj : false,
  msie : false,
  editing : false,
  hover_elem : null
};

util = {

  filter_items : function(o) {
    var objs = {};
    var currentObjects;
    var current = undefined;
    var key;
    var new_objs;
    var backend_objs;
    if (o == 1) {
      for ( var i in state.backend_attributes)
        objs[i] = state.backend_attributes[i];
      currentObjects = $(".btn_attr");
      key = logic.key_attr;
      if (state.attr_index != -1)
        current = $("#attr_" + state.attr_index).data(key);
      backend_objs = state.backend_attributes;
      new_objs = state.new_attributes;
    } else {
      for ( var i in state.backend_objects)
        objs[i] = state.backend_objects[i];
      currentObjects = $(".btn_obj");
      key = logic.key_obj;
      if (state.obj_index != -1)
        current = $("#obj_" + state.obj_index).data(key);
      backend_objs = state.backend_objects;
      new_objs = state.new_objects;
    }

    if (current)
      current = current.id;
    for ( var i = 0; i < currentObjects.length; ++i) {
      var obj = $(currentObjects[i]);
      if (obj.data(key)) {
        // console.debug(obj.data());
        if (obj.data(key).id in backend_objs) {
          if (current != objs[obj.data(key).id].id)
            delete objs[obj.data(key).id];
        }
      }
    }
    for ( var i in new_objs)
      objs[i] = new_objs[i];
    for ( var i = 0; i < currentObjects.length; ++i) {
      var obj = $(currentObjects[i]);
      if (obj.data(key)) {
        if (obj.data(key).id in new_objs) {
          if (current != objs[obj.data(key).id].id) {
            delete objs[obj.data(key).id];
          }
        }
      }
    }
    // 
    return objs;
  },

  filter_l_lobjects : function(object) {
    var l_objs = {};
    var obj_los = {};
    for ( var i in object.learningObjects) {
      obj_los[object.learningObjects[i].id] = object.learningObjects[i];
    }
    for ( var i in state.backend_l_objects)
      if (!(i in obj_los))
        l_objs[i] = state.backend_l_objects[i];
    return l_objs;
  },

  replace_objects : function(obj) {
    for ( var o in obj) {
      var object = {
        "name" : obj[o].name,
        "description" : obj[o].description,
        "id" : o,
        "learningObjects" : obj[o].learningObjects
      };
      state.backend_objects[o] = object;
      for ( var i in object.learningObjects) {
        state.active_l_objects[object.learningObjects[i].id] = object.learningObjects[i];
      }
      var currentObjects = $(".btn_obj");
      for ( var i = 0; i < currentObjects.length; ++i) {
        if ($.data(currentObjects[i], logic.key_obj).id == obj[o].id) {
          $(currentObjects[i]).data(logic.key_obj, object);
        }
      }
      delete state.new_objects[obj[o].id];
    }
  },

  replace_attributes : function(obj) {
    for ( var o in obj) {
      var object = {
        "name" : obj[o].name,
        "description" : obj[o].description,
        "id" : o,
        "learningObjects" : obj[o].learningObjects
      };
      for ( var i in object.learningObjects) {
        state.active_l_objects[object.learningObjects[i].id] = object.learningObjects[i];
      }
      state.backend_attributes[o] = object;
      var currentObjects = $(".btn_attr");
      for ( var i = 0; i < currentObjects.length; ++i) {
        if ($.data(currentObjects[i], logic.key_attr).id == obj[o].id) {
          $(currentObjects[i]).data(logic.key_attr, object);
        }
      }
      delete state.new_attributes[obj[o].id];
    }
  },

  replace_items : function(obj, o) {
    for ( var n in obj) {

      var object = {
        "name" : obj[n].name,
        "description" : obj[n].description,
        "id" : n,
        "learningObjects" : obj[n].learningObjects
      };
      (o == 1) ? state.backend_attributes[n] = object : state.backend_objects[n] = object;
      for ( var i in object.learningObjects) {
        state.active_l_objects[object.learningObjects[i].id] = object.learningObjects[i];
      }
      var currentObjects;
      var key;
      (o == 1) ? key = logic.key_attr : key = logic.key_obj;
      (o == 1) ? currentObjects = $(".btn_attr") : currentObjects = $(".btn_obj");
      for ( var i = 0; i < currentObjects.length; ++i) {

        if ($.data(currentObjects[i], key).id == obj[n].id) {
          $(currentObjects[i]).data(key, object);
        }
      }
    }
    for ( var n in obj)
      (o == 1) ? delete state.new_attributes[obj[n].id] : delete state.new_objects[obj[n].id];
  },

  underConstruction : function(selector) {
    $(selector).css({
      "background-image" : "url(" + state.basedir + "img/uc.png)",
      "background-size" : "100% 100%"
    });
  },

  getSize : function(obj) {
    var size = 0;
    for ( var key in obj) {
      if (obj.hasOwnProperty(key))
        size++;
    }
    return size;
  },
  // taken from
  // http://stackoverflow.com/questions/1584370/how-to-merge-two-arrays-in-javascript
  unique : function(array) {
    var a = array.concat();
    for ( var i = 0; i < a.length; ++i) {
      for ( var j = i + 1; j < a.length; ++j) {
        if (a[i] === a[j])
          a.splice(j--, 1);
      }
    }
    return a;
  },

  containsConcept : function(array, concept) {
    for (i in array) {
      if (concept.id == array[i].id)
        return true;
    }
    return false;
  }
};

backend = {
  url : "",
  path_get_objects : "getObjects",
  path_get_attributes : "getAttributes",
  path_get_l_objects : "getLearningObjects",
  path_create_l_objects : "createLearningObjects",
  path_create_objects : "createObjects",
  path_create_attributes : "createAttributes",
  path_create_domain : "createDomain",
  path_get_domainheaders : "getDomainHeaders",
  path_get_domain : "getDomain",
  path_update_object : "updateObject",
  path_update_attribute : "updateAttribute",
  path_update_concept : "updateConcept",
  path_identify : "identify",

  identify : function(payload, callback) {
    $.ajax({
      cache : false,
      type : "POST",
      url : backend.url + backend.path_identify,
      data : payload,
      dataType : "json",
      contentType : "application/json; charset=utf-8",
      success : callback
    });
  },

  get_objects : function(callback) {
    $.ajax({
      cache : false,
      type : "GET",
      url : backend.url + backend.path_get_objects,
      success : function(obj) {
        for ( var id in obj) {
          obj[id].id = id;
          state.backend_objects[id] = obj[id];
          for ( var i in obj[id].learningObjects) {
            state.active_l_objects[obj[id].learningObjects[i].id] = obj[id].learningObjects[i];
          }
        }
        if (callback)
          callback();
        // 
        // state.backend_objects = obj;
      },
      error : function(obj) {
        console.error(JSON.stringify(obj));
      }
    });
  },

  get_attributes : function(callback) {
    $.ajax({
      cache : false,
      type : "GET",
      url : backend.url + backend.path_get_attributes,
      success : function(obj) {
        for ( var id in obj) {
          obj[id].id = id;
          state.backend_attributes[id] = obj[id];
          for ( var i in obj[id].learningObjects) {
            state.active_l_objects[obj[id].learningObjects[i].id] = obj[id].learningObjects[i];
          }
        }
        if (callback)
          callback();
      },
      error : function(obj) {
        console.error(JSON.stringify(obj));
      }
    });
  },

  get_l_objects : function(callback) {
    $.ajax({
      cache : false,
      type : "GET",
      url : backend.url + backend.path_get_l_objects,
      success : function(obj) {
        for ( var id in obj) {
          obj[id].id = id;
          state.backend_l_objects[id] = obj[id];
        }
        if (callback)
          callback();
      },
      error : function(obj) {
        console.error(JSON.stringify(obj));
      }
    });
  },

  create_l_objects : function(payload, callback) {
    $.ajax({
      cache : false,
      type : "POST",
      url : backend.url + backend.path_create_l_objects,
      data : payload,
      dataType : "json",
      contentType : "application/json; charset=utf-8",
      success : callback
    });
  },

  create_objects : function(payload, callback) {
    $.ajax({
      cache : false,
      type : "POST",
      url : backend.url + backend.path_create_objects,
      data : payload,
      dataType : "json",
      contentType : "application/json; charset=utf-8",
      success : callback
    });
  },

  create_attributes : function(payload, callback) {
    $.ajax({
      cache : false,
      type : "POST",
      url : backend.url + backend.path_create_attributes,
      data : payload,
      dataType : "json",
      contentType : "application/json; charset=utf-8",
      success : callback
    });
  },

  create_domain : function(payload, callback) {

    $.ajax({
      cache : false,
      type : "POST",
      url : backend.url + backend.path_create_domain,
      data : payload,
      dataType : "json",
      contentType : "application/json; charset=utf-8",
      success : function(obj) {
        callback(obj);
      }
    });

  },

  update_attribute : function(payload, callback) {
    $.ajax({
      cache : false,
      type : "POST",
      url : backend.url + backend.path_update_attribute,
      data : payload,
      dataType : "json",
      contentType : "application/json; charset=utf-8",
      success : function(obj) {
        callback(obj);
      }
    });
  },

  update_object : function(payload, callback) {
    $.ajax({
      cache : false,
      type : "POST",
      url : backend.url + backend.path_update_object,
      data : payload,
      dataType : "json",
      contentType : "application/json; charset=utf-8",
      success : function(obj) {
        callback(obj);
      }
    });
  },

  update_concept : function(payload, callback) {
    $.ajax({
      cache : false,
      type : "POST",
      url : backend.url + backend.path_update_concept,
      data : payload,
      dataType : "json",
      contentType : "application/json; charset=utf-8",
      success : function(obj) {
        callback(obj);
      }
    });
  },

  get_domains : function(callback) {
    $.ajax({
      cache : false,
      type : "GET",
      url : backend.url + backend.path_get_domainheaders,
      success : function(obj) {
        callback(obj);
      },
      error : function(obj) {
        console.error(JSON.stringify(obj));
      }
    });
  },

  get_domain : function(id, callback) {
    var payload = {
      "id" : id
    };
    $.ajax({
      cache : false,
      type : "GET",
      data : payload,
      contentType : "text/plain; charset=utf-8",
      url : backend.url + backend.path_get_domain,
      success : function(obj) {
        callback(obj);
      },
      error : function(obj) {
        console.error(JSON.stringify(obj));
      }
    });
  }
};

logic = {
  key_obj : "object",
  key_attr : "attribute",
  key_lo : "l_object",

  init : function(basedir, backend_url) {
    state.basedir = basedir;
    state.user = elgg.get_logged_in_user_entity();

    backend.url = backend_url;
    $(window).resize(function() {
      ui.resize();
    });

    backend.identify(JSON.stringify({
      "name" : state.user.name,
      "description" : state.user.url,
      "externalUID" : state.user.guid.toString(),
      "teacher" : true
    }));

    for ( var i = 0; i < $(".btn_obj").length; ++i) {
      var btn = $($(".btn_obj").get(i));
      ui.setup_hover_obj(btn);
    }

    for ( var i = 0; i < $(".btn_attr").length; ++i) {
      var btn = $($(".btn_attr").get(i));
      ui.setup_hover_attr(btn);
    }
    if ($(".msie_fca").length > 0)
      state.msie = true;
    // IE COMPAT
    if (!console.debug) {
      console.debug = console.log;
    }
    // IE COMPAT taken from https://github.com/jaubourg/ajaxHooks
    if (state.msie) {
      jQuery.ajaxTransport(function(s) {
        if (s.crossDomain && s.async) {
          if (s.timeout) {
            s.xdrTimeout = s.timeout;
            delete s.timeout;
          }
          var xdr;
          return {
            send : function(_, complete) {
              function callback(status, statusText, responses, responseHeaders) {
                xdr.onload = xdr.onerror = xdr.ontimeout = jQuery.noop;
                xdr = undefined;
                complete(status, statusText, responses, responseHeaders);
              }
              xdr = new XDomainRequest();
              xdr.onload = function() {
                callback(200, "OK", {
                  text : xdr.responseText
                }, "Content-Type: " + xdr.contentType);
              };
              xdr.onerror = function() {
                callback(404, "Not Found");
              };
              xdr.onprogress = jQuery.noop;
              xdr.ontimeout = function() {
                callback(0, "timeout");
              };
              xdr.timeout = s.xdrTimeout || Number.MAX_VALUE;
              xdr.open(s.type, s.url);
              xdr.send((s.hasContent && s.data) || null);
            },
            abort : function() {
              if (xdr) {
                xdr.onerror = jQuery.noop;
                xdr.abort();
              }
            }
          };
        }
      });
      ui.display_ie_warning();
    } else {
      try {
        if (XDomainRequest)
          ui.display_ie_warning(1);
      } catch (error) {
      }
    }

    $("#dia_set_obj").dialog({
      autoOpen : false,
      height : 320,
      width : 510,
      resizable : false,
      modal : true
    });
    $("#dia_set_attr").dialog({
      autoOpen : false,
      height : 320,
      width : 510,
      resizable : false,
      modal : true
    });
    $("#dia_set_dom").dialog({
      autoOpen : false,
      height : 200,
      width : 400,
      resizable : false,
      modal : true
    });
    $("#dia_set_lo").dialog({
      autoOpen : false,
      height : 200,
      width : 400,
      resizable : false,
      modal : true
    });
    $("#dia_add_obj").dialog({
      autoOpen : false,
      height : 200,
      width : 400,
      resizable : false,
      modal : true
    });
    $("#dia_add_attr").dialog({
      autoOpen : false,
      height : 200,
      width : 400,
      resizable : false,
      modal : true
    });
    $("#dia_rem_attr").dialog({
      autoOpen : false,
      height : 200,
      width : 400,
      resizable : false,
      modal : true,
    });
    $("#dia_rem_obj").dialog({
      autoOpen : false,
      height : 200,
      width : 400,
      resizable : false,
      modal : true,
    });
    $("#dia_create_obj").dialog({
      autoOpen : false,
      height : 200,
      width : 400,
      resizable : false,
      modal : true
    });
    $("#dia_create_attr").dialog({
      autoOpen : false,
      height : 200,
      width : 400,
      resizable : false,
      modal : true
    });

    $("#dia_create_domain").dialog({
      autoOpen : false,
      height : 200,
      width : 400,
      resizable : false,
      modal : true
    });

    $("#dia_create_lo").dialog({
      autoOpen : false,
      height : 200,
      width : 400,
      resizable : false,
      modal : true
    });

    $("#dia_vis").dialog({
      autoOpen : false,
      height : 160,
      resizable : false,
      modal : false,
      dialogClass : 'no-close',
      closeOnEscape : false,
      beforeclose : function() {
        return false;
      }
    }).dialogExtend({
      minimize : true,
      maximize : false
    });

    var tmp_w = $(".btn_attr").width();
    var tmp_h = $(".btn_attr").height();
    $(".td_attr").css("width", tmp_h + 15);
    $(".td_attr").css("height", tmp_w + 15);
    if (state.msie) {
      console.debug("OH NO, IE! initilaizing objects and attributes on-demand");

    } else {
      backend.get_objects();
      backend.get_attributes();
      backend.get_l_objects();
    }

  },

  create_object : function(name, description) {
    $("#input_create_obj_name").prop("value", "");
    $("#input_create_obj_description").prop("value", "");
    var now = Date.now();
    var object = {
      "name" : name,
      "description" : description,
      "id" : now
    };

    state.new_objects[now] = object;
    $("#dia_create_obj").dialog("close");
    $("#dia_set_obj").dialog("close");
    ui.set_item(state.obj_index, 0);
  },

  remove_lo : function(lo, object, select, o) {
    for ( var i in object.learningObjects) {
      if (object.learningObjects[i] == lo) {
        /*
         * var id = select.options[select.selectedIndex].value; if (o == 1) {
         * 
         * if (id in state.backend_attributes) {
         * state.backend_attributes[id].learningObjects[i].splice(i, 1); } else {
         * state.new_attributes[id].learningObjects[i].splice(i, 1); } } else {
         * if (id in state.backend_objects) {
         * state.backend_objects[id].learningObjects[i].splice(i, 1); } else {
         * state.new_objects[id].learningObjects[i].splice(i, 1); } }
         */
        delete state.active_l_objects[object.learningObjects[i].id];
        object.learningObjects.splice(i, 1);
        break;
      }
    }
    logic.save_item(object.name, select, object.description, o);
  },

  set_l_object : function(object, select, o) {
    // console.debug("set_LO");
    $(".item_description").empty();
    var l_objs = util.filter_l_lobjects(object);
    var sel = $("#sel_set_lo").empty();

    for ( var i in l_objs) {
      sel.create("option", {
        value : JSON.stringify(l_objs[i])
      }).create("txt", l_objs[i].name);
    }
    sel.create("option").create("txt", "<New Learning Object>");
    sel.prop("selectedIndex", -1);
    $("#dia_set_lo").dialog("open");
    util.underConstruction("#dia_set_lo");
    $("#dia_set_lo_content").css("background", "rgba(255,255,255,0.6)");
    // $("#dia_set_lo").removeData();

    $("#dia_set_lo").data(logic.key_lo, {
      "object" : object,
      "select" : select,
      "o" : o
    });
    // object.learningObjects.push({
    // id : Date.now(),
    // name : "LO " + Math.floor((Math.random() * Date.now()) / 590),
    // description : "LO dummy " + Date.now()
    // });
    // logic.save_item(object.name, select, object.description, o);
  },

  set_lo : function() {
    var data = $("#dia_set_lo").data(logic.key_lo);
    var sel = $("#sel_set_lo").get(0);
    data.object.learningObjects.push(JSON.parse(sel.options[sel.selectedIndex].value));
    for ( var i in data.object.learningObjects) {
      state.active_l_objects[data.object.learningObjects[i].id] = data.object.learningObjects[i];
    }
    $("#dia_set_lo").dialog("close");
    logic.save_item(data.object.name, data.select, data.object.description, data.o);
  },

  save_item : function(name, select, description, o) {
    var create = select.selectedIndex == -1;
    if (create) {
      var now = Date.now();
      var object = {
        "name" : name,
        "description" : description,
        "id" : now,
        "learningObjects" : []
      };
      // ui.cancel_item_edit(select, o);
      if (o == 1) {
        state.new_attributes[now] = object;
        // $("#attr_" + state.attr_index).data(logic.key_attr, object);
        ui.set_item(state.attr_index, o, now);
      } else {
        state.new_objects[now] = object;
        // $("#obj_" + state.attr_index).data(logic.key_obj, object);
        ui.set_item(state.obj_index, o, now);
      }
    } else {
      var update = false;
      if (o == 1)
        update = select.options[select.selectedIndex].value in state.backend_attributes;
      else
        update = select.options[select.selectedIndex].value in state.backend_objects;
      if (update) {
        if (o == 1) {
          var obj = state.backend_attributes[select.options[select.selectedIndex].value];
          obj.name = name;
          obj.description = description;
          // delete obj["learningObjects"];
          backend.update_attribute(JSON.stringify(obj), function(resp) {
            // console.debug(resp);
            state.backend_attributes[select.options[select.selectedIndex].value] = resp;
            ui.set_item(state.attr_index, o, select.options[select.selectedIndex].value);
          });
        } else {
          var obj = state.backend_objects[select.options[select.selectedIndex].value];
          obj.name = name;
          obj.description = description;
          // delete obj["learningObjects"];
          backend.update_object(JSON.stringify(obj), function(resp) {
            // console.debug(resp);
            state.backend_objects[select.options[select.selectedIndex].value] = resp;
            ui.set_item(state.obj_index, o, select.options[select.selectedIndex].value);
          });
        }
      } else {
        if (o == 1) {
          var obj = state.new_attributes[select.options[select.selectedIndex].value];
          obj.name = name;
          obj.description = description;
          state.new_attributes[select.options[select.selectedIndex].value] = obj;
          ui.set_item(state.attr_index, o, select.options[select.selectedIndex].value);
        } else {
          var obj = state.new_objects[select.options[select.selectedIndex].value];
          obj.name = name;
          obj.description = description;
          state.new_objects[select.options[select.selectedIndex].value] = obj;
          ui.set_item(state.obj_index, o, select.options[select.selectedIndex].value);
        }
      }
    }
  },

  choose_object : function() {
    // console.debug("choose");
    var select = $("#sel_set_obj").get(0);
    var id = select.options[select.selectedIndex].value;
    $("#dia_set_obj").dialog("close");
    var object;
    if (id in state.backend_objects)
      object = state.backend_objects[id];
    else
      object = state.new_objects[id];
    $("#obj_" + state.obj_index).prop("value", object.name).data(logic.key_obj, object);
    state.obj_index = -1;
    util.filter_items(0);
  },

  choose_attribute : function() {
    // console.debug("choose");
    var select = $("#sel_set_attr").get(0);
    var id = select.options[select.selectedIndex].value;
    $("#dia_set_attr").dialog("close");
    var object;
    if (id in state.backend_attributes)
      object = state.backend_attributes[id];
    else
      object = state.new_attributes[id];
    $("#attr_" + state.attr_index).prop("value", object.name).data(logic.key_attr, object);
    state.attr_index = -1;
    util.filter_items(1);
  },

  create_mapping : function(name, description) {

    var mapping = {};
    var orderedAttribs = [];
    var orderedObjects = [];

    var attributes = $(".btn_attr");
    for ( var i = 0; i < attributes.length; ++i) {

      orderedAttribs.push($("#" + attributes[i].id).data(logic.key_attr).id);
    }

    var objects = $(".btn_obj");
    for ( var i = 0; i < objects.length; ++i) {

      orderedObjects.push($("#" + objects[i].id).data(logic.key_obj).id);
    }

    var checks = $(":checkbox:checked");
    for ( var c = 0; c < checks.length; ++c) {

      try {
        var btn = $("#obj_" + (checks[c]).id.split("_")[1]);
        var obj = btn.data(logic.key_obj);

        var attr = $("#attr_" + (checks[c]).id.split("_")[3]).data(logic.key_attr);
        if (!(obj.id.toString() in mapping)) {
          mapping[obj.id.toString()] = [];

        } else {

        }
        mapping[obj.id.toString()].push(attr.id);

      } catch (error) {
        // 
      }
    }

    var result = {
      "name" : name,
      "description" : description,
      "attributes" : orderedAttribs,
      "objects" : orderedObjects,
      "mapping" : mapping
    };

    return result;
  },

  rem_object : function(index) {

    $("#dia_rem_obj").dialog("close");
    $("#tr_obj_" + index).remove();
    var tmp_w = $(".btn_attr").width();
    var tmp_h = $(".btn_attr").height();
    $(".td_attr").css("width", tmp_h + 15);
    $(".td_attr").css("height", tmp_w + 15);
  },

  rem_attribute : function(index) {

    $("#dia_rem_attr").dialog("close");
    $(".td_attr_" + index).remove();
    var tmp_w = $(".btn_attr").width();
    var tmp_h = $(".btn_attr").height();
    $(".td_attr").css("width", tmp_h + 15);
    $(".td_attr").css("height", tmp_w + 15);
  },

  create_domain : function(name, description) {
    logic.save(function() {
      var domain = logic.create_mapping(name, description);
      domain.externalUID = state.user.guid.toString();
      backend.create_domain(JSON.stringify(domain), function(obj) {

        $("#dia_create_domain").dialog("close");
        alert("Domain successfully saved!");
        state.domain = obj;
        ui.display_lattice();
      });
    });
  },

  create_lo : function(name, description) {
    // var data =$("#dia_set_lo").data(logic.key_lo);
    var sel = document.getElementById("sel_set_lo");
    // $(sel).empty();
    if ((description.substring(0, 7) != "http://") && (description.substring(0, 8) != "https://"))
      description = "http://" + description;
    var lo = {
      "name" : name,
      "description" : description,
      "id" : Date.now(),
      "externalUID" : state.user.guid
    };

    // this is not pretty!
    // on the other hand, anything more would be a waste since learnign object
    // are pretty undefined as of now
    backend.create_l_objects(JSON.stringify([ lo ]), function(obj) {
      for ( var i in obj) {
        lo.id = i;
      }
      // console.debug(lo);
      state.backend_l_objects[lo.id] = lo;
      var opt = document.createElement("option");
      opt.text = name;
      opt.value = JSON.stringify(lo);
      $(sel).prepend($(opt));

      // state.new_l_objects[lo.id] = lo;
      $("#dia_create_lo").dialog("close");
      // sel.selectedIndex = sel.selectedIndex - 1;
    });
  },

  save : function(callback) {
    var currentObjects = $(".btn_obj");
    for ( var i = 0; i < currentObjects.length; ++i) {
      if (!$.hasData(currentObjects[i])) {
        alert("Some objects are undefined!");
        return false;
      }
    }

    currentObjects = $(".btn_attr");
    for ( var i = 0; i < currentObjects.length; ++i) {
      if (!$.hasData(currentObjects[i])) {
        alert("Some attributes are undefined!");
        return false;
      }
    }

    var objects = [];
    for ( var i in state.new_objects)
      objects.push(state.new_objects[i]);

    var attributes = [];
    for ( var i in state.new_attributes)
      attributes.push(state.new_attributes[i]);

    backend.create_objects(JSON.stringify(objects), function(obj) {

      util.replace_objects(obj);
      backend.create_attributes(JSON.stringify(attributes), function(attr) {

        util.replace_attributes(attr);
        if (callback)
          callback();
      });
    });
  },

  load : function(domainid) {
    $("#dia_set_dom").dialog("close");
    // state.domain = domain;
    // state.new
    backend
        .get_domain(
            domainid,
            function(domain) {
              state.domain = domain;

              // console.debug(JSON.stringify(domain));
              var num_attributes = Object.keys(domain.mapping.attributes).length;
              var num_objects = Object.keys(domain.mapping.objects).length;

              while ($(".td_attr").length < num_attributes) {
                ui.append_attribute();
              }
              while ($(".btn_del_obj").length < num_objects) {
                ui.append_object();
              }

              for ( var currentAttribs = $(".td_attr").length; currentAttribs > num_attributes; currentAttribs = $(".td_attr").length) {

                var index = $(".td_attr")[0].childNodes[2].id.split("_")[1];

                logic.rem_attribute(index);

              }
              while ($(".btn_del_obj").length > num_objects) {
                logic.rem_object($($(".btn_del_obj")[0]).prop("id").split("_")[3]);
              }
              backend.get_objects(backend.get_attributes(function() {
                setTimeout(function() {
                  var index = 0;
                  for ( var a in domain.mapping.attributes) {

                    var id = "#attr_" + $(".td_attr")[index].childNodes[2].id.split("_")[1];

                    var attribute = JSON.parse(a);
                    // console.debug(a);
                    // console.debug(attribute);
                    $(id).data(logic.key_attr, attribute);
                    $(id).prop("value", attribute.name);
                    ++index;
                  }

                  $(".check").prop("checked", false);
                  var objects = $(".btn_del_obj");
                  index = 0;
                  for ( var o in domain.mapping.objects) {
                    var id = "#obj_" + ($(objects[index]).prop("id").split("_")[3]);

                    var object = $.parseJSON(o);

                    $(id).data(logic.key_obj, object);
                    $(id).prop("value", object.name);
                    var mapped = domain.mapping.objects[o];

                    for ( var m = 0; m < mapped.length; ++m) {

                      var currentA = $(".btn_attr");
                      for ( var a = 0; a < currentA.length; ++a) {
                        // console.debug(currentA[a]);
                        // console.debug("#" + currentA[a].id);
                        // console.debug($("#" +
                        // currentA[a].id).data(logic.key_attr));
                        // console.debug(mapped[m]);
                        if ($("#" + currentA[a].id).data(logic.key_attr).id == mapped[m].id) {
                          $(id + "_" + currentA[a].id).prop("checked", true);
                        }
                      }
                    }

                    ++index;
                  }
                  if (state.msie) {
                    setTimeout(function() {
                      ui.display_lattice();
                    }, 300);
                  } else
                    ui.display_lattice();
                }, state.msie ? 1000 : 0);
              }));
            });
  }
};

ui = {

  move_down : function(id) {
    if ($("#tr_obj_" + id).next().prop("id"))
      $("#tr_obj_" + id).next().after($("#tr_obj_" + id));
    $(".btn_move_right").hide();
    $(".btn_move_left").hide();
    $(".btn_move_up").hide();
    $(".btn_move_down").hide();
  },
  move_up : function(id) {

    if ($("#tr_obj_" + id).prev().prop("id"))
      $("#tr_obj_" + id).prev().before($("#tr_obj_" + id));
    $(".btn_move_right").hide();
    $(".btn_move_left").hide();
    $(".btn_move_up").hide();
    $(".btn_move_down").hide();
  },

  move_left : function(id) {
    var td = $(".td_attr_" + id);
    if (!$(td.get(0)).prev().prop("class"))
      return;
    td.each(function() {
      var prev = $(this).prev();
      $(this).detach().insertBefore(prev);
    });
    $(".btn_move_right").hide();
    $(".btn_move_left").hide();
    $(".btn_move_up").hide();
    $(".btn_move_down").hide();
  },

  move_right : function(id) {
    var td = $(".td_attr_" + id);
    if (!(td.next().next().get(0)))
      return;
    td.each(function() {
      var next = $(this).next();

      $(this).detach().insertAfter(next);
    });
    $(".btn_move_right").hide();
    $(".btn_move_left").hide();
    $(".btn_move_up").hide();
    $(".btn_move_down").hide();
  },

  setup_hover_obj : function(btn) {
    $(".btn_move_down").hover(function() {
      state.hover = 1;
    }, function() {
      setTimeout(function() {
        if (state.hover != null) {
          state.hover = null;
          $(".btn_move_down").hide();
          $(".btn_move_up").hide();
        }
      }, 100);
    });
    $(".btn_move_up").hover(function() {
      state.hover = 1;
    }, function() {
      setTimeout(function() {
        if (state.hover != null) {

          state.hover = null;
          $(".btn_move_down").hide();
          $(".btn_move_up").hide();
        }
      }, 100);
    });
    btn.hover(function() {
      state.hover = null;
      setTimeout(function() {
        $(".btn_move_down").hide();
        $(".btn_move_up").hide();
        $("#btn_move_up_" + btn.prop("id").split("_")[1]).show();
        $("#btn_move_down_" + btn.prop("id").split("_")[1]).show();
      }, 150);
    }, function() {
      setTimeout(function() {
        if (!state.hover) {
          $(".btn_move_down").hide();
          $(".btn_move_up").hide();
        }
      }, 150);
    });
  },

  setup_hover_attr : function(btn) {
    $(".btn_move_left").hover(function() {
      state.hover = 1;
    }, function() {
      setTimeout(function() {
        if (state.hover != null) {
          state.hover = null;
          $(".btn_move_left").hide();
          $(".btn_move_right").hide();
        }
      }, 100);
    });
    $(".btn_move_right").hover(function() {
      state.hover = 1;
    }, function() {
      setTimeout(function() {
        if (state.hover != null) {

          state.hover = null;
          $(".btn_move_left").hide();
          $(".btn_move_right").hide();
        }
      }, 100);
    });
    btn.hover(function() {
      state.hover = null;
      setTimeout(function() {
        $(".btn_move_left").hide();
        $(".btn_move_right").hide();
        $("#btn_move_right_" + btn.prop("id").split("_")[1]).show();
        $("#btn_move_left_" + btn.prop("id").split("_")[1]).show();
      }, 150);
    }, function() {
      setTimeout(function() {
        if (!state.hover) {
          $(".btn_move_left").hide();
          $(".btn_move_right").hide();
        }
      }, 150);
    });
  },

  create_lo : function(o) {
    $("#dia_create_lo").dialog("open");
    $("#input_create_lo_name").val("");
    $("#input_create_lo_description").val("");
    util.underConstruction("#dia_create_lo");
  },

  set_item : function(index, o, id) {
    ui.prepare_dialog(o);
    (o == 1) ? state.attr_index = index : state.obj_index = index;
    // console.debug("set item");
    var key;
    (o == 1) ? key = logic.key_attr : key = logic.key_obj;
    var data;
    (o == 1) ? data = $("#attr_" + index) : data = $("#obj_" + index);
    // console.debug(data.data(key));
    if (!id && data.data(key))
      id = data.data(key).id;
    // console.debug(id);
    var inited = (o == 1) ? state.inited_attr : state.inited_obj;
    if (state.msie && !(inited)) {
      if (o == 1) {
        state.inited_attr = true;
        backend.get_attributes(function() {
          ui.set_item(index, o, id);
        });
      } else {
        state.inited_obj = true;
        backend.get_objects(function() {
          ui.set_item(index, o, id);
        });
      }
    } else {
      var selectedIndex = -1;
      var objects = util.filter_items(o);
      // console.debug(objects);
      var sel;
      (o == 1) ? sel = $("#sel_set_attr") : sel = $("#sel_set_obj");
      sel.empty();
      var i = -1;
      for ( var obj in objects) {
        ++i;
        // console.debug(id);
        // console.debug(objects[obj].id);
        if (id && (objects[obj].id == id))
          selectedIndex = i;
        sel.create("option", {
          value : obj
        }).create("txt", objects[obj].name);
      }
      var newitem;
      (o == 1) ? newitem = "<New Attribute>" : newitem = "<New Object>";
      sel.create("option").create("txt", newitem);
      sel.prop("selectedIndex", selectedIndex);
      // console.debug("index: " + selectedIndex);
      (o == 1) ? $("#dia_set_attr").dialog("open") : $("#dia_set_obj").dialog("open");
      if (selectedIndex != -1) {
        ui.display_item_description(document.getElementById(sel.prop("id")), o);
      }
    }
  },

  prepare_dialog : function(o) {

    state.editing = false;
    var btn;
    var btn_cancel;
    if (o == 1) {
      btn = $("#btn_choose_attr_ok");
      btn_cancel = $("#btn_choose_attr_cancel");
    } else {
      btn = $("#btn_choose_obj_ok");
      btn_cancel = $("#btn_choose_obj_cancel");
    }
    btn.removeProp("onclick");
    btn_cancel.removeProp("onclick");
    btn.unbind("click");
    btn_cancel.unbind("click");
    btn.val(elgg.echo('wespot_fca:ok'));
    btn_cancel.click(function() {
      if (o == 1)
        $('#dia_set_attr').dialog('close');
      else
        $('#dia_set_obj').dialog('close');
    });
    btn.click(function() {
      if (o == 1)
        logic.choose_attribute();
      else
        logic.choose_object();
    });
    $(".text_description").empty();
    $(".descr_detail").hide();
    $(".btn_edit").hide();
    $("#input_name").remove();
    if (o == 1)
      $("#sel_set_attr").show();
    else
      $("#sel_set_obj").show();
  },

  add_object : function() {
    $(".item_description").empty();
    $("#dia_add_obj").dialog("open");
  },

  add_attribute : function() {
    $(".item_description").empty();
    $("#dia_add_attr").dialog("open");
  },

  append_attribute : function() {

    var id = ++state.id_last_attr;

    $("<col class=\"td_attr_" + id + "\">").insertBefore($("#col_tail"));
    var i = 0;
    var tails = $(".tail");
    var cb;
    var len = tails.length;

    for ( var elem in tails) {
      if (i == 0) {
        $(
            "<td class=\"right td_attr_"
                + id
                + "\"><input type=\"image\" src=\""
                + state.basedir
                + "img/delete.svg\" "
                + " width=\"16px\" height= \"16px\" alt=\"x\" title=\"Delete Attribute\" id=\"btn_del_attr_"
                + id + "\" class=\"input btn_del_attr\" onclick=\"ui.rem_attribute(" + id
                + ")\" /></td>").insertBefore($(tails[elem]));
      } else if (i == 1) {
        $(
            "<td class=\"td_attr td_attr_"
                + id
                + "\"><input type=\"image\" src=\""
                + state.basedir
                + "img/left.svg\" id=\"btn_move_left_"
                + id
                + "\""
                + " width=\"16px\" height=\"40px\" alt=\"&lt;\" title=\"Move Left\" class=\"input btn_move_left\" onclick=\"ui.move_left("
                + id
                + ")\" />"
                + "<input type=\"image\" src=\""
                + state.basedir
                + "img/right.svg\" "
                + "id=\"btn_move_right_"
                + id
                + "\" width=\"16px\" height=\"40px\" alt=\"&gt;\" title=\"Move Right\" class=\"input btn_move_right\""
                + " onclick=\"ui.move_right(" + id + ")\" /><input type=\"button\" id=\"attr_" + id
                + "\" " + "class=\"input fullheight btn_attr col\" value=\"Dummy Attribute "
                + (id + 1) + "\" onclick=\"ui.set_item(" + id + ",1)\" /></td>").insertBefore(
            $(tails[elem]));
        ui.setup_hover_attr($("#attr_" + id));
      } else if (i < len - 1) {

        $("<td></td>").insertBefore($(tails[elem]));
        cb = $(tails[elem]).prev();
        var obj_index = cb.parent().prop("id").split("_")[2];
        cb.prop("class", "td_attr_" + id);
        cb.create("input", {
          "type" : "checkbox",
          "class" : "input check",
          "id" : "obj_" + obj_index + "_attr_" + id
        });

      } else if (i == len - 1) {
        $("<td class=\"td_attr_" + id + "\"></td>").insertBefore($(tails[elem]));
      }
      ++i;
    }
  },

  append_object : function() {

    var id = ++state.id_last_obj;
    var tail = $(".obj_tail");
    var tr = $("<tr id=\"tr_obj_" + id + "\" class=\"tr_obj\"></tr>");
    tr.insertBefore(tail);
    var attrs = $(".btn_attr");
    var td = tr.create("td", {
      "class" : "left"
    });

    td.create("input", {
      "type" : "image",
      "src" : state.basedir + "img/up.svg",
      "alt" : "^",
      "title" : "Move Up",
      "id" : "btn_move_up_" + id,
      "class" : "input btn_move_up",
      "onclick" : "ui.move_up(" + id + ")",
      "width" : "40px",
      "height" : "16px"
    });
    td.create("input", {
      "type" : "image",
      "src" : state.basedir + "img/down.svg",
      "alt" : "v",
      "title" : "Move Down",
      "id" : "btn_move_down_" + id,
      "class" : "input btn_move_down",
      "onclick" : "ui.move_down(" + id + ")",
      "width" : "40px",
      "height" : "16px"
    });

    td.create("input", {
      "type" : "image",
      "src" : state.basedir + "img/delete.svg",
      "alt" : "x",
      "title" : "Remove Object",
      "id" : "btn_del_obj_" + id,
      "class" : "input btn_del_obj",
      "onclick" : "ui.rem_object(" + id + ")"
    });
    ui.setup_hover_obj(td.create("input", {
      "type" : "button",
      "id" : "obj_" + id,
      "class" : "input btn_obj",
      "value" : "Dummy Object " + (id + 1),
      "onclick" : "ui.set_item(" + id + ",0)"
    }));

    for ( var a = 0; a < attrs.length; ++a) {

      var attr_id = $(attrs[a]).prop("id").split("_")[1];
      tr.append("<td class=\"td_attr_" + attr_id
          + "\"><input type=\"checkbox\" class=\"input check\" id=\"obj_" + id + "_attr_" + attr_id
          + "\" /></td>");
    }
    tr.append("<td class=\"tail\" style=\"background-color: #fff\"></td>");
  },

  rem_attribute : function(index) {
    if ($(".td_attr").length == 1) {
      alert("Cannot remove the only Attribute!");
      return;
    }

    $(".item_description").empty();
    $("#span_rem_attr").empty();

    $("#span_rem_attr").create("txt", $("#attr_" + index).prop("value"));
    $("#btn_rem_attr_yes").click(function() {
      logic.rem_attribute(index);
    });
    $("#dia_rem_attr").dialog("open");
  },

  rem_object : function(index) {
    if ($(".btn_del_obj").length == 1) {
      alert("Cannot remove the only Object!");
      return;
    }
    $(".item_description").empty();
    $("#span_rem_obj").empty();

    $("#span_rem_obj").create("txt", $("#obj_" + index).prop("value"));
    $("#btn_rem_obj_yes").click(function() {
      logic.rem_object(index);
    });
    $("#dia_rem_obj").dialog("open");
  },

  create_object : function() {
    $(".item_description").empty();
    $("#dia_create_obj").dialog("open");
  },

  create_attribute : function() {
    $(".item_description").empty();
    $("#dia_create_attr").dialog("open");
  },

  display_description : function(select, o) {
    $(".item_description").empty();
    if (select.selectedIndex == (select.options.length - 1)) {
      if (o == 0) {
        ui.create_lo(o);
        select.selectedIndex = -1;
      }
    } else if (select.selectedIndex != -1) {
      if ((o == 0) || (o == 2)) {
        var obj = JSON.parse(select.options[select.selectedIndex].value);
        $(".item_description").create("txt", obj.description);
        if (obj.owner) {
          $(".item_description").create("br");
          $(".item_description").create("txt",
              "(" + elgg.echo('wespot_fca:created_by') + " " + obj.owner.name + ")");
        }
      }
    }
  },

  set_lo : function(object, select, o) {
    $("#btn_choose_lo_ok").unbind("click");
    $("#btn_choose_lo_ok").click(function() {
      logic.save_item(object.name, select, object.description, o);// name,
    });
  },

  create_lo_div : function(lo, object, div_lo, select, o) {
    // console.debug(lo);
    // console.debug(div_lo.get());
    var div = div_lo.create("div", {
      id : "lo_" + lo.id,
      "class" : "span_lo"
    });
    var tdiv = div.create("div", {
      "class" : "txt_lo"
    }).click(function() {
      window.open(lo.description);
    });
    tdiv.create("txt", lo.name);
    var buttons = div.create("div", {
      class : "div_lo_buttons"
    });
    buttons.create("input", {
      type : "image",
      "class" : "input btn_lo",
      src : state.basedir + "img/edit.svg",
      width : "16px",
      height : "16px"
    }).click(function() {

    });
    buttons.create("input", {
      type : "image",
      "class" : "input btn_lo",
      src : state.basedir + "img/delete.svg",
      width : "16px",
      height : "16px"
    }).click(function() {
      logic.remove_lo(lo, object, select, o);
    });
    div.hover(function() {
      ui.hide_lo_buttons();
      ui.show_lo_buttons(this);
    }, function() {
      ui.hide_lo_buttons();
    });
  },

  display_learning_objects : function(object, select, o) {
    // console.debug("lo");
    // console.debug(o);
    var div_lo;
    (o == 1) ? div_lo = $("#lo_attr") : div_lo = $("#lo_obj");
    div_lo.empty();

    for ( var i in object.learningObjects) {
      ui.create_lo_div(object.learningObjects[i], object, div_lo, select, o);
    }
    div_lo.append("<br>");
    div_lo.create("input", {
      type : "image",
      src : state.basedir + "img/add.svg",
      width : "22px",
      height : "22px",
      class : "input btn_add_lo"
    }).click(function() {
      logic.set_l_object(object, select, o);
      // name,
      // select,
      // description,
      // o
      // ui.display_learning_objects(object, o);
    });
  },

  display_item_description : function(select, o) {
    // ui.hide_lo_buttons();
    // console.debug("description " + o);
    util.underConstruction(".div_lo");
    $(".div_lo").empty();
    $(select).show();
    $(".btn_edit").show();
    var textarea;
    if (o == 1)
      textarea = document.getElementById("text_descr_attr");
    else
      textarea = document.getElementById("text_descr_obj");
    $(".text_description").empty();
    $(".descr_detail").show();
    $(".text_description").show();
    $(textarea).prop("readonly", true);

    if (select.selectedIndex == (select.options.length - 1)) {
      ui.display_item_edit(select, textarea, o);
    } else if (select.selectedIndex != -1) {
      var obj = (o == 1) ? $("#attr_" + state.attr_index).data(logic.key_attr) : $(
          "#obj_" + state.obj_index).data(logic.key_obj);
      // console.debug(obj);

      if (obj) {
        for ( var index in select.options) {
          if (select.options[index].value == obj) {
            select.selectedIndex = index;
            break;
          }
        }
      }
      if (o == 1) {
        try {
          $(".text_description").val(
              state.backend_attributes[select.options[select.selectedIndex].value].description);
          ui.display_learning_objects(
              state.backend_attributes[select.options[select.selectedIndex].value], select, o);
        } catch (not_an_error) {
          $(".text_description").val(
              state.new_attributes[select.options[select.selectedIndex].value].description);
          ui.display_learning_objects(
              state.new_attributes[select.options[select.selectedIndex].value], select, o);
        }
      } else {
        try {
          $(".text_description").val(
              state.backend_objects[select.options[select.selectedIndex].value].description);
          ui.display_learning_objects(
              state.backend_objects[select.options[select.selectedIndex].value], select, o);
        } catch (not_an_error) {
          $(".text_description").val(
              state.new_objects[select.options[select.selectedIndex].value].description);
          ui.display_learning_objects(
              state.new_objects[select.options[select.selectedIndex].value], select, o);
        }
      }
    }

  },

  show_lo_buttons : function(lo) {
    // $(this).css("border-color", "#888");
    $(lo).children(".txt_lo").css("border-bottom-right-radius", "5px");
    $(lo).children(".txt_lo").css("background-color", "#fff");

    $(lo).children(".div_lo_buttons").css("z-index", "1");
  },

  hide_lo_buttons : function(lo) {
    $(".div_lo_buttons").css("z-index", "-1");
    $(".txt_lo").css("border-bottom-right-radius", "3px");
    $(".txt_lo").css("background-color", "rgba(255,255,255,0.9)");
    // $(".span_lo").css("border", "1px solid #FFF");
  },
  cancel_item_edit : function(select, o) {
    state.editing = false;
    ui.prepare_dialog(o);
    $(select).show();
    ui.display_item_description(select, o);
    if (select.selectedIndex == -1) {
      $(".descr_detail").hide();
      $(".btn_edit").hide();
    }

  },

  prepare_item_edit : function(select, textarea, o) {
    $(".btn_edit").hide();
    var btn;
    var btn_cancel;
    if (o == 1) {
      btn = $("#btn_choose_attr_ok");
      btn_cancel = $("#btn_choose_attr_cancel");
    } else {
      btn = $("#btn_choose_obj_ok");
      btn_cancel = $("#btn_choose_obj_cancel");
    }
    btn.removeProp("onclick");
    btn_cancel.removeProp("onclick");
    btn.unbind("click");
    btn_cancel.unbind("click");
    btn.val(elgg.echo('wespot_fca:save'));
    btn.click(function() {
      logic.save_item($("#input_name").val(), select, $(textarea).val(), o);
    });

    btn_cancel.click(function() {
      ui.cancel_item_edit(select, o);
    });

    $(select).hide();
    var td = $(select).parent();
    var input = td.create("input", {
      type : "text",
      id : "input_name",
      class : "sel_set",
    });
    $(textarea).prop("readonly", false);
    return input;
  },

  display_item_edit : function(select, textarea, o) {
    var create = select.selectedIndex == (select.options.length - 1);
    state.editing = !state.editing;
    var name;
    if (!create) {
      if (o == 1) {
        try {
          name = state.backend_attributes[select.options[select.selectedIndex].value].name;
        } catch (not_an_error) {
          name = state.new_attributes[select.options[select.selectedIndex].value].name;
        }
      } else {
        try {
          name = state.backend_objects[select.options[select.selectedIndex].value].name;
        } catch (not_an_error) {
          name = state.new_objects[select.options[select.selectedIndex].value].name;
        }
      }
    } else {
      name = "";
      $(textarea).val("");
      select.selectedIndex = -1;
    }
    ui.prepare_item_edit(select, textarea, o).val(name).focus();

  },

  list_domains : function(domains) {
    $("#sel_set_dom").empty();
    for ( var id in domains) {
      domains[id].id = id;
      $("#sel_set_dom").create("option", {
        value : JSON.stringify(domains[id])
      }).append(domains[id].name);
    }
    $("#sel_set_dom").prop("selectedIndex", "-1");
    $("#dia_set_dom").dialog("open");
    $(".item_description").empty();
  },

  display_lattice : function() {
    lattice.init("#canvas_lattice", $(window).width() - 350, $(window).height() - 100,
        "#div_lattice_info", backend);
    lattice.draw();
    $("#vis_loading").show();
    $("#canvas_lattice").hide();
    $("#div_lattice_info").hide();
    setTimeout(function() {
      $("#vis_loading").hide();
      $("#canvas_lattice").show();
      $("#div_lattice_info").show(100);
      lattice.switch_view();
    }, 1500);
    $("#cb_latticeview").prop("checked", false);
    // lattice.sys.stop();
    try {
      $("#dia_vis").dialogExtend("restore");
    } catch (error) {

    }
    $("#dia_vis").dialog("option", "title",
        elgg.echo('wespot_fca:lattice:tax') + " '" + state.domain.name + "'");
    $("#dia_vis").dialog("option", "width", $("#canvas_lattice").prop("width") + 240);
    $("#dia_vis").dialog("option", "height", $("#canvas_lattice").prop("height") + 50);
    try {
      $("#dia_vis").dialog("open").dialogExtend("restore");
    } catch (error) {
    }
    $("#dia_vis").fadeTo(0, 0);
    $("#dia_vis").fadeTo(1000, 1);

  },
  resize : function() {
    // TODO this is a hack, but sicne elgg ships an ancient version on jQuery we
    // are limited to hacks
    var state = $("#dia_vis").data("dialog-state");

    lattice.resize($(window).width() - 350, $(window).height() - 100);
    $("#dia_vis").dialog("option", "width", $("#canvas_lattice").prop("width") + 240);
    $("#dia_vis").dialog("option", "height", $("#canvas_lattice").prop("height") + 50);
    if (state == "minimized") {
      $("#dia_vis").dialogExtend("minimize");
    }
  },

  display_ie_warning : function(flag) {
    var y;

    flag ? y = 180 : y = 200;
    var text = "<hr/>Consider switching to <a class=\"iewarn\" target=\"_blank\" "
        + "href=\"https://www.google.com/chrome\">Google Chrome</a> or "
        + "<a class=\"iewarn\" target=\"_blank\" href=\"http://www.mozilla.org/firefox/\">Mozilla Firefox</a>";
    if (!flag)
      text = "Warning, your Browser is not fully supported! You will experience bad performance and display problems!"
          + text;
    else
      text = "Warning, your Browser is not fully supported! This may lead to rendering glitches, layout problems and other minor annoyances"
          + text;
    $("#dia_vis").parent().create("div", {
      id : "dia_ie_warn",
      title : "Compatibility Warning",
    }).append(text);
    $("#dia_ie_warn").dialog({
      autoOpen : true,
      height : y,
      modal : true,
      width : 350
    });
  }

};
