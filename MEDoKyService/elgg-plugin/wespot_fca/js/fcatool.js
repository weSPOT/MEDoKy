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
  item_id : undefined,
  domain : [],
  inited_attr : false,
  inited_obj : false,
  msie : false,
  editing : false,
  hover_elem : null,
  gid : -1,
  g_name : "",
  owner_id : -1,
  teacher : false,
  load_domain : true
};

entity_types = {
  object : 0,
  attribute : 1,
  domain : 2,
  learningobject : 3
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
  path_get_learner_domain : "getLearnerDomain",
  path_update_object : "updateObject",
  path_update_attribute : "updateAttribute",
  path_update_concept : "updateConcept",
  path_update_valuation : "updateValuations",
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
    $
        .ajax({
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
          },
          error : function(obj) {
            console.error(JSON.stringify(obj));
          }
        });
  },

  get_attributes : function(callback) {
    $
        .ajax({
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

  update_valuation : function(payload, callback) {
    $.ajax({
      cache : false,
      type : "POST",
      url : backend.url + backend.path_update_valuation,
      data : payload,
      dataType : "json",
      contentType : "application/json; charset=utf-8",
      success : function(obj) {
        callback(obj);
      }
    });
  },

  get_domains : function(id, callback) {
    var payload = {
      "id" : id
    };
    $.ajax({
      cache : false,
      type : "GET",
      data : payload,
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
  },

  get_learner_domain : function(did, uid, callback) {
    var payload = {
      "id" : did,
      "externalUID" : uid
    };
    $.ajax({
      cache : false,
      type : "GET",
      data : payload,
      contentType : "text/plain; charset=utf-8",
      url : backend.url + backend.path_get_learner_domain,
      success : function(obj) {
        callback(obj);
      },
      error : function(obj) {
        console.error(JSON.stringify(obj));
      }
    });
  }
};

util = {
  parse_params : function() {
    var params = window.location.search.replace("?", "").split("&");
    if (params.length < 3)
      window.alert("Error! Please Launch the FCA from a group!");
    for ( var i in params) {
      var param = params[i].split("=");
      if (param[0] == "gid")
        state.gid = param[1];
      else if (param[0] == "name")
        state.g_name = param[1];
      else if (param[0] == "uid")
        state.owner_id = parseInt(param[1]);
      else if (param[0] == "blank")
        state.load_domain = false;
    }
    if (state.owner_id == state.user.guid)
      state.teacher = true;
  },

  setup_msie : function() {
    if ($(".msie_fca").length > 0)
      state.msie = true;
    // IE COMPAT
    if (!console.debug) {
      console.debug = console.log;
    }
    // IE COMPAT taken from https://github.com/jaubourg/ajaxHooks
    if (state.msie) {
      jQuery
          .ajaxTransport(function(s) {
            if (s.crossDomain && s.async) {
              if (s.timeout) {
                s.xdrTimeout = s.timeout;
                delete s.timeout;
              }
              var xdr;
              return {
                send : function(_, complete) {
                  function callback(status, statusText, responses,
                      responseHeaders) {
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
  },

  switch_student : function() {
    $("#btn_open").removeProp("onclick");
    $("#btn_open").click(function() {
      backend.get_domains(state.gid, ui.list_domains);
    });
    $("#btn_save").hide();
    $("#btn_new").hide();
    $("#main_table").hide();
    $("#dia_vis").hide();
    $("#span_latticeview").css({
      "margin-top" : "75px",
      "padding-left" : "7px"
    });
  },

  filter_items : function(entityType) {
    var objs = {};
    var current = undefined;

    var prefix_id = "#obj_";
    var key = logic.key_obj;
    var currentObjects = $(".btn_obj");
    var entities = state.backend_objects;
    var new_entities = state.new_objects;
    var entity_index = state.obj_index;

    if (entityType == entity_types.attribute) {
      key = logic.key_attr;
      currentObjects = $(".btn_attr");
      entities = state.backend_attributes;
      new_entities = state.new_attributes;
      entity_index = state.attr_index;
      prefix_id = "#attr_";
    }

    for ( var i in entities)
      objs[i] = entities[i];
    if (entity_index != -1)
      current = $(prefix_id + entity_index).data(key);

    if (current)
      current = current.id;

    for (var i = 0; i < currentObjects.length; ++i) {
      var obj = $(currentObjects[i]);
      if (obj.data(key)) {
        if (obj.data(key).id in entities) {
          if (current != objs[obj.data(key).id].id)
            delete objs[obj.data(key).id];
        }
      }
    }

    for ( var i in new_entities)
      objs[i] = new_entities[i];

    for (var i = 0; i < currentObjects.length; ++i) {
      var obj = $(currentObjects[i]);
      if (obj.data(key)) {
        if (obj.data(key).id in new_entities) {
          if (current != objs[obj.data(key).id].id) {
            delete objs[obj.data(key).id];
          }
        }
      }
    }
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

  replace_items : function(obj, entityType) {
    var entities = state.backend_objects;
    var new_entities = state.new_objects;
    var id_buttons = ".btn_obj";
    var key = logic.key_obj;
    if (entityType == entity_types.attribute) {
      entities = state.backend_attributes;
      new_entities = state.new_attributes;
      id_buttons = ".btn_attr";
      key = logic.key_attr;
    }

    for ( var o in obj) {
      var object = {
        "name" : obj[o].name,
        "description" : obj[o].description,
        "id" : o,
        "learningObjects" : obj[o].learningObjects
      };
      entities[o] = object;
      for ( var i in object.learningObjects) {
        state.active_l_objects[object.learningObjects[i].id] = object.learningObjects[i];
      }
      var currentObjects = $(id_buttons);
      for (var i = 0; i < currentObjects.length; ++i) {
        if ($.data(currentObjects[i], key).id == obj[o].id) {
          $(currentObjects[i]).data(key, object);
        }
      }
      delete new_entities[obj[o].id];
    }
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
    for (var i = 0; i < a.length; ++i) {
      for (var j = i + 1; j < a.length; ++j) {
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

logic = {
  key_obj : "object",
  key_attr : "attribute",
  key_lo : "l_object",

  init : function(basedir, backend_url, files) {
    state.basedir = basedir;
    state.files = files;
    state.user = elgg.get_logged_in_user_entity();
    util.parse_params();

    backend.url = backend_url;
    $(window).resize(function() {
      ui.resize();
    });

    backend.identify(JSON.stringify({
      "name" : state.user.name,
      "description" : state.user.url,
      "externalUID" : state.user.guid.toString(),
      "teacher" : state.teacher
    }));

    ui.setup_btn_hover();
    ui.prepare_dialogs();
    util.setup_msie();

    var tmp_w = $(".btn_attr").width();
    var tmp_h = $(".btn_attr").height();
    $(".td_attr").css("width", tmp_h + 15);
    $(".td_attr").css("height", tmp_w + 15);

    var los = [];
    for ( var f in state.files) {
      var lo = {
        "name" : state.files[f].name,
        "description" : state.files[f].description.replace(/(<([^>]+)>)/ig, ""),
        "data" : state.files[f].data,
        "id" : Date.now(),
        "externalUID" : state.user.guid
      };
      los.push(lo);
    }

    backend
        .create_l_objects(
            JSON.stringify(los),
            function(obj) {
              backend
                  .get_l_objects(function() {
                    if (!state.teacher) {
                      util.switch_student();
                      if (state.load_domain)
                        backend.get_domains(state.gid, ui.show_initial_domain);
                    } else {
                      if (state.load_domain)
                        backend.get_domains('-1', ui.show_initial_domain);
                    }
                    if (state.msie) {
                      console
                          .debug("OH NO, IE! initilaizing objects and attributes on-demand");

                    } else {
                      backend.get_objects(function() {
                        backend.get_attributes(function() {
                        });
                      });
                    }
                  });
            });
  },

  remove_lo : function(lo, object, o) {
    for ( var i in object.learningObjects) {
      if (object.learningObjects[i] == lo) {
        delete state.active_l_objects[object.learningObjects[i].id];
        object.learningObjects.splice(i, 1);
        break;
      }
    }
    logic.save_item(object, o);
  },

  set_l_object : function(object, o) {
    $(".item_description").empty();
    var l_objs = util.filter_l_lobjects(object);
    var sel = $("#sel_set_lo").empty();

    sel.create("option").prop("disabled", true).create("txt",
        "<New " + elgg.echo("wespot_fca:l_obj") + ">");
    for ( var i in l_objs) {
      sel.create("option", {
        value : JSON.stringify(l_objs[i])
      }).create("txt", l_objs[i].name);
    }
    sel.prop("selectedIndex", -1);
    $("#dia_set_lo").dialog("open");
    $("#dia_set_lo_content").css("background", "rgba(255,255,255,0.6)");

    $("#dia_set_lo").data(logic.key_lo, {
      "object" : object,
      "o" : o
    });
  },

  set_lo : function() {
    var data = $("#dia_set_lo").data(logic.key_lo);
    var sel = $("#sel_set_lo").get(0);
    data.object.learningObjects.push(JSON
        .parse(sel.options[sel.selectedIndex].value));
    for ( var i in data.object.learningObjects) {
      state.active_l_objects[data.object.learningObjects[i].id] = data.object.learningObjects[i];
    }

    $("#dia_set_lo").dialog("close");
    logic.save_item(data.object, data.o);
  },
  save_item : function(object, entityType) {

    var items = state.backend_objects;
    var new_items = state.new_objects;
    var index = state.obj_index;
    var updatefunc = backend.update_object;
    if (entityType == 1) {
      items = state.backend_attributes;
      new_items = state.new_attributes;
      index = state.attr_index;
      updatefunc = backend.update_attribute;
    }

    if (object.id in items) { // update!
      var obj = items[object.id];
      obj.name = object.name;
      obj.description = object.description;
      for ( var l in obj.learningObjects) {
        // check yourself before you wreck yourself
        if (obj.learningObjects[l].owner) {
          obj.learningObjects[l].owner.objects = {}; // cannot parse
          obj.learningObjects[l].owner.attributes = {};
        }
      }
      updatefunc(JSON.stringify(obj), function(resp) {
        items[object.id] = resp;
        ui.set_item(index, entityType, object.id);
      });
    } else {
      var obj = new_items[object.id];
      obj.name = object.name;
      obj.description = object.description;
      ui.set_item(index, entityType, object.id);
    }

  },

  choose_item : function(entityType) {
    var dia = $("#dia_set_obj");
    var entities = state.backend_objects;
    var new_entities = state.new_objects;
    var index = state.obj_index;
    var prefix = "#obj_";
    var key = logic.key_obj;
    if (entityType == entity_types.attribute) {
      dia = $("#dia_set_attr");
      entities = state.backend_attributes;
      new_entities = state.new_attributes;
      index = state.attr_index;
      prefix = "#attr_";
      key = logic.key_attr;
    }
    dia.dialog("close");
    var id = state.item_id;
    var item;
    if (id in entities) {
      item = entities[id];
    } else
      item = new_entities[id];

    $(prefix + index).prop("value", item.name).data(key, item);
    index = -1;
    util.filter_items(entityType);
  },

  create_mapping : function(name, description) {

    var mapping = {};
    var orderedAttribs = [];
    var orderedObjects = [];

    var attributes = $(".btn_attr");
    for (var i = 0; i < attributes.length; ++i) {

      orderedAttribs.push($("#" + attributes[i].id).data(logic.key_attr).id);
    }

    var objects = $(".btn_obj");
    for (var i = 0; i < objects.length; ++i) {

      orderedObjects.push($("#" + objects[i].id).data(logic.key_obj).id);
    }

    var checks = $(":checkbox:checked");
    for (var c = 0; c < checks.length; ++c) {

      try {
        var btn = $("#obj_" + (checks[c]).id.split("_")[1]);
        var obj = btn.data(logic.key_obj);

        var attr = $("#attr_" + (checks[c]).id.split("_")[3]).data(
            logic.key_attr);
        if (!(obj.id.toString() in mapping)) {
          mapping[obj.id.toString()] = [];

        } else {

        }
        mapping[obj.id.toString()].push(attr.id);

      } catch (error) {
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
      domain.externalCourseID = state.gid;
      domain.courseName = state.g_name;
      backend.create_domain(JSON.stringify(domain), function(obj) {

        $("#dia_create_domain").dialog("close");
        alert("Domain successfully saved!");
        state.domain = obj;
        ui.display_lattice();
      });
    });
  },

  create_lo : function(name, description, data) {

    var sel = document.getElementById("sel_set_lo");
    $(sel.options[0]).prop("disabled", true);
    if ((data.substring(0, 7) != "http://")
        && (data.substring(0, 8) != "https://"))
      data = "http://" + data;
    var lo = {
      "name" : name,
      "description" : description,
      "data" : data,
      "id" : Date.now(),
      "externalUID" : state.user.guid
    };

    // this is not pretty!
    // on the other hand, anything more would be a waste since learnign object
    // are pretty undefined as of now
    backend.create_l_objects(JSON.stringify([ lo ]), function(obj) {
      for ( var i in obj) {
        lo = obj[i];
        lo.id = i;
      }
      state.backend_l_objects[lo.id] = lo;
      var opt = document.createElement("option");
      opt.text = name;
      opt.value = JSON.stringify(lo);
      $(sel).append($(opt));
      sel.selectedIndex = sel.options.length - 1;
      $("#dia_create_lo").dialog("close");

    });
  },

  save : function(callback) {
    var currentObjects = $(".btn_obj");
    for (var i = 0; i < currentObjects.length; ++i) {
      if (!$.hasData(currentObjects[i])) {
        alert("Some objects are undefined!");
        return false;
      }
    }

    currentObjects = $(".btn_attr");
    for (var i = 0; i < currentObjects.length; ++i) {
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

      util.replace_items(obj, entity_types.object);
      backend.create_attributes(JSON.stringify(attributes), function(attr) {

        util.replace_items(attr, entity_types.attribute);
        if (callback)
          callback();
      });
    });
  },

  populate_domain : function(domain, teacher) {

    state.domain = domain;
    $("#h_domain_name").empty().create("txt", domain.name);
    var num_attributes = Object.keys(domain.mapping.attributes).length;
    var num_objects = Object.keys(domain.mapping.objects).length;

    while ($(".td_attr").length < num_attributes) {
      ui.append_attribute();
    }
    while ($(".btn_del_obj").length < num_objects) {
      ui.append_object();
    }

    for (var currentAttribs = $(".td_attr").length; currentAttribs > num_attributes; currentAttribs = $(".td_attr").length) {
      var index = $(".td_attr")[0].childNodes[2].id.split("_")[1];
      logic.rem_attribute(index);
    }
    while ($(".btn_del_obj").length > num_objects) {
      logic.rem_object($($(".btn_del_obj")[0]).prop("id").split("_")[3]);
    }
    backend
        .get_objects(backend
            .get_attributes(function() {
              setTimeout(
                  function() {
                    var index = 0;
                    for ( var a in domain.mapping.attributes) {

                      var id = "#attr_"
                          + $(".td_attr")[index].childNodes[2].id.split("_")[1];

                      var attribute = JSON.parse(a);
                      $(id).data(logic.key_attr, attribute);
                      $(id).prop("value", attribute.name);
                      ++index;
                    }

                    $(".check").prop("checked", false);
                    var objects = $(".btn_del_obj");
                    index = 0;
                    for ( var o in domain.mapping.objects) {
                      var id = "#obj_"
                          + ($(objects[index]).prop("id").split("_")[3]);
                      var object = $.parseJSON(o);

                      $(id).data(logic.key_obj, object);
                      $(id).prop("value", object.name);
                      var mapped = domain.mapping.objects[o];

                      for (var m = 0; m < mapped.length; ++m) {
                        var currentA = $(".btn_attr");
                        for (var a = 0; a < currentA.length; ++a) {
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
                        if (teacher)
                          $("#dia_vis").dialogExtend("minimize");
                      }, 300);
                    } else {
                      ui.display_lattice();
                      if (teacher)
                        $("#dia_vis").dialogExtend("minimize");
                    }
                  }, 1000);
            }));
  },

  load : function(domainid, teacher) {
    $("#dia_set_dom").dialog("close");
    if (state.teacher) {
      backend.get_domain(domainid, function(domain) {
        logic.populate_domain(domain, teacher);
      });
    } else {
      backend.get_learner_domain(domainid, state.user.guid.toString(),
          function(domain) {
            logic.populate_domain(domain);
          });
    }
  }
};

ui = {

  prepare_dialogs : function() {

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
      width : 450,
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

    if (state.teacher) {

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
    }

  },

  setup_btn_hover : function() {
    for (var i = 0; i < $(".btn_obj").length; ++i) {
      var btn = $($(".btn_obj").get(i));
      ui.setup_hover_obj(btn);
    }

    for (var i = 0; i < $(".btn_attr").length; ++i) {
      var btn = $($(".btn_attr").get(i));
      ui.setup_hover_attr(btn);
    }
  },

  enable_options : function(select) {
    $(select.options[0]).prop("disabled", false);

  },

  disable_options : function(select) {

    $(select.options[0]).prop("disabled", true);
  },
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
  },

  set_item : function(index, entityType, id) {
    ui.prepare_dialog(entityType);
    (entityType == entity_types.attribute) ? state.attr_index = index
        : state.obj_index = index;

    var sel = $("#sel_set_obj");
    var key = logic.key_obj;
    var data = $("#obj_" + index);
    var inited = state.inited_obj;
    if (entityType == entity_types.attribute) {
      key = logic.key_attr;
      data = $("#attr_" + index);
      inited = state.inited_attr;
      sel = $("#sel_set_attr");
    }
    state.item_id = undefined;

    if (!id && data.data(key))
      id = data.data(key).id;
    if (id)
      ui.display_item_description(id, entityType);

    if (state.msie && !(inited)) {
      if (entityType == entity_types.attribute) {
        state.inited_attr = true;
        backend.get_attributes(function() {
          ui.set_item(index, entityType, id);
        });
      } else {
        state.inited_obj = true;
        backend.get_objects(function() {
          ui.set_item(index, entityType, id);
        });
      }
    } else {
      var objects = util.filter_items(entityType);

      var items = [];
      for ( var obj in objects) {
        items.push({
          label : objects[obj].name,
          value : objects[obj].name,
          data : obj
        });
      }

      sel.autocomplete({
        // this is needebe because of the old jQueryUI version used
        source : function(request, response) {
          var results = $.ui.autocomplete.filter(items, request.term);
          results.splice(0, 0, {
            value : request.term,
            label : "create " + request.term
          });
          response(results);
        },

        select : function(event, ui) {
          if (!ui.item.data)
            window.ui.prepare_item_edit(entityType, true);
          else {
            $(this).blur();
            window.ui.display_item_description(ui.item.data, entityType);
          }
        }
      });
      (entityType == entity_types.attribute) ? $("#dia_set_attr")
          .dialog("open") : $("#dia_set_obj").dialog("open");

    }
    sel.blur();
  },

  prepare_dialog : function(entityType) {

    state.editing = false;
    var btn;
    var btn_cancel;

    if (entityType == entity_types.attribute) {
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
      if (entityType == entity_types.attribute)
        $('#dia_set_attr').dialog('close');
      else
        $('#dia_set_obj').dialog('close');
    });
    btn.click(function() {
      logic.choose_item(entityType);
    });
    $(".text_description").empty();
    $(".descr_detail").hide();
    $(".btn_edit").hide();

    var sel = (entityType == entity_types.attribute) ? $("#sel_set_attr")
        : $("#sel_set_obj");

    sel.blur(function() {
      $(this).val($("body").data("item").name);
    });
    sel.click(function() {
      $(this).val("");
      // ui.set_item(state.attr_index, entityType);
    });

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
        var td = $(document.createElement("td"));
        td.addClass("right td_attr_" + id);
        td.create("input", {
          type : "image",
          src : state.basedir + "img/delete.svg",
          width : "16px",
          height : "16px",
          alt : "x",
          title : elgg.echo('wespot_fca:attr:rem'),
          id : "btn_del_attr_" + id,
          class : "input btn_del_attr",
          onclick : "ui.rem_attribute(" + id + ")"
        });
        td.insertBefore($(tails[elem]));
      } else if (i == 1) {
        var td = $(document.createElement("td"));
        td.addClass("td_attr td_attr_" + id);
        td.create("input", {
          type : "image",
          src : state.basedir + "img/left.svg",
          id : "btn_move_left_" + id,
          width : "16px",
          height : "40px",
          alt : "&lt",
          title : elgg.echo('wespot_fca:move_left'),
          class : "input btn_move_left",
          onclick : "ui.move_left(" + id + ")"
        });
        td.create("input", {
          type : "image",
          src : state.basedir + "img/right.svg",
          id : "btn_move_right_" + id,
          width : "16px",
          height : "40px",
          alt : "&gt",
          title : elgg.echo('wespot_fca:move_right'),
          class : "input btn_move_right",
          onclick : "ui.move_left(" + id + ")",
        });
        td.create("input", {
          type : "button",
          id : "attr_" + id,
          class : "input btn_attr col",
          value : elgg.echo('wespot_fca:obj:dummy') + " " + (id + 1),
          onclick : "ui.set_item(" + id + ",entity_types.attribute)"
        });
        td.insertBefore($(tails[elem]));
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
        $("<td class=\"td_attr_" + id + "\"></td>")
            .insertBefore($(tails[elem]));
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
      "title" : elgg.echo('wespot_fca:move_up'),
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
      "title" : elgg.echo('wespot_fca:move_down'),
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
      "title" : elgg.echo('wespot_fca:obj:rem'),
      "id" : "btn_del_obj_" + id,
      "class" : "input btn_del_obj",
      "onclick" : "ui.rem_object(" + id + ")"
    });
    ui.setup_hover_obj(td.create("input", {
      "type" : "button",
      "id" : "obj_" + id,
      "class" : "input btn_obj",
      "value" : elgg.echo('wespot_fca:obj:dummy') + " " + (id + 1),
      "onclick" : "ui.set_item(" + id + ",entity_types.object)"
    }));

    for (var a = 0; a < attrs.length; ++a) {

      var attr_id = $(attrs[a]).prop("id").split("_")[1];
      tr.append("<td class=\"td_attr_" + attr_id
          + "\"><input type=\"checkbox\" class=\"input check\" id=\"obj_" + id
          + "_attr_" + attr_id + "\" /></td>");
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

  display_description : function(select, entityType) {
    $(".item_description").empty();
    if (select.selectedIndex == 0) {
      if (entityType == entity_types.learningobject) {
        ui.create_lo(entityType);
        select.selectedIndex = -1;
      } else if (entityType == entity_types.domain) {
        var str_domain = select.options[select.selectedIndex].value;
        if (str_domain != "-1") {
          ui.display_domain_description(select);
        } else {
          $("#btn_choose_dom_ok").prop("disabled", true);
        }
      }
    } else if (select.selectedIndex != -1) {
      if (entityType == entity_types.domain) {
        var str_domain = select.options[select.selectedIndex].value;
        if (str_domain != "-1") {
          ui.display_domain_description(select);
        } else {
          $("#btn_choose_dom_ok").prop("disabled", true);
        }
      } else if (entityType == entity_types.learningobject) {
        ui.display_domain_description(select);
      }
    }
  },

  display_domain_description : function(select) {

    var obj = JSON.parse(select.options[select.selectedIndex].value);
    $("#btn_choose_dom_ok").prop("disabled", false);
    $(".item_description").create("txt", obj.description);
    if (obj.owner) {
      $(".item_description").create("br");
      $(".item_description")
          .create(
              "txt",
              "(" + elgg.echo('wespot_fca:created_by') + " " + obj.owner.name
                  + ")");
    }
  },

  set_lo : function(object, select, entityType) {
    $("#btn_choose_lo_ok").unbind("click");
    $("#btn_choose_lo_ok").click(function() {
      logic.save_item(object.name, select, object.description, entityType);// name,
    });
  },

  create_lo_div : function(lo, object, div_lo, entityType) {

    var div = div_lo.create("div", {
      id : "lo_" + lo.id,
      "class" : "span_lo"
    });
    var tdiv = div.create("div", {
      "class" : "txt_lo"
    }).click(function() {
      window.open(lo.data, "Learning Object", "width=800,height=600");
    });
    tdiv.create("txt", lo.name);
    var buttons = div.create("div", {
      class : "div_lo_buttons"
    });
    /*
     * buttons.create("input", { type : "image", "class" : "input btn_lo", src :
     * state.basedir + "img/edit.svg", width : "16px", height : "16px"
     * }).click(function() {
     * 
     * });
     */
    buttons.create("input", {
      type : "image",
      "class" : "input btn_lo",
      src : state.basedir + "img/delete.svg",
      width : "16px",
      height : "16px"
    }).click(function() {
      logic.remove_lo(lo, object, entityType);
    });
    div.hover(function() {
      ui.hide_lo_buttons();
      ui.show_lo_buttons(this);
    }, function() {
      ui.hide_lo_buttons();
    });
  },

  display_learning_objects : function(object, entityType) {

    var div_lo;
    (entityType == entity_types.attribute) ? div_lo = $("#lo_attr")
        : div_lo = $("#lo_obj");
    div_lo.empty();

    for ( var i in object.learningObjects) {
      ui.create_lo_div(object.learningObjects[i], object, div_lo, entityType);
    }
    div_lo.append("<br>");
    div_lo.create("input", {
      type : "image",
      src : state.basedir + "img/add.svg",
      width : "22px",
      height : "22px",
      class : "input btn_add_lo"
    }).click(function() {
      logic.set_l_object(object, entityType);
    });
  },

  display_item_description : function(id, entityType) {

    $(".div_lo").empty();
    $(".btn_edit").show();
    var textarea = document.getElementById("text_descr_obj");
    var entities = state.backend_objects;
    var new_entities = state.new_objects;
    var textfield = $("#sel_set_obj");
    if (entityType == entity_types.attribute) {
      textarea = document.getElementById("text_descr_attr");
      entities = state.backend_attributes;
      new_entities = state.new_attributes;
      textfield = $("#sel_set_attr");
    }
    // textfield.blur();
    state.item_id = id;
    $(".text_description").empty();
    $(".descr_detail").show();
    $(".text_description").show();
    $(textarea).prop("readonly", true);
    try {
      $(".text_description").val(entities[id].description);
      ui.display_learning_objects(entities[id], entityType);
      textfield.val(entities[id].name);
      $("body").data("item", entities[id]);
    } catch (not_an_error) {
      $("body").data("item", new_entities[id]);
      $(".text_description").val(new_entities[id].description);
      ui.display_learning_objects(new_entities[id], entityType);
      textfield.val(new_entities[id].name);
    }
    console.debug($("body").data("item"));
  },

  show_lo_buttons : function(lo) {
    $(lo).children(".txt_lo").css("border-bottom-right-radius", "5px");
    $(lo).children(".txt_lo").css("background-color", "#fff");

    $(lo).children(".div_lo_buttons").css("z-index", "1");
  },

  hide_lo_buttons : function(lo) {
    $(".div_lo_buttons").css("z-index", "-1");
    $(".txt_lo").css("border-bottom-right-radius", "3px");
    $(".txt_lo").css("background-color", "rgba(255,255,255,0.9)");
  },

  cancel_item_edit : function(entityType, item) {
    state.editing = false;

    $("body").removeData("item");
    var sel = $("#sel_set_obj");
    var index = state.obj_index;
    var textarea = $("#text_descr_obj");
    if (entityType == entity_types.attribute) {
      sel = $("#sel_set_attr");
      textarea = $("#text_descr_attr");
      index = state.attr_index;
    }
    textarea.val(item.description);
    sel.val(item.name);
    // $(".descr_detail").hide();
    // ui.prepare_dialog(entityType);
    ui.set_item(index, entityType, item.id);
  },

  prepare_item_edit : function(entityType, clear) {
    if (clear) {
      $(".div_lo").empty();
      $(".text_description").val("");
    }
    $(".btn_edit").hide();
    var sel = $("#sel_set_obj");
    var textarea = $("#text_descr_obj");
    var btn = $("#btn_choose_obj_ok");
    var btn_cancel = $("#btn_choose_obj_cancel");
    var new_items = state.new_objects;
    if (entityType == entity_types.attribute) {
      sel = $("#sel_set_attr");
      textarea = $("#text_descr_attr");
      btn = $("#btn_choose_attr_ok");
      btn_cancel = $("#btn_choose_attr_cancel");
      new_items = state.new_attributes;
    }
    try {
      sel.autocomplete("destroy");
    } catch (not_an_error) {
    }
    sel.unbind("click");
    sel.unbind("blur");
    btn.removeProp("onclick");
    btn_cancel.removeProp("onclick");
    btn.unbind("click");
    btn_cancel.unbind("click");
    btn.val(elgg.echo('wespot_fca:save'));
    btn.click(function() {
      var item = $("body").data("item");
      $("body").removeData("item");
      if (!item) {
        var item = {
          id : Date.now(),
          learningObjects : []
        };
        new_items[item.id] = item;
      }
      item.name = sel.val();
      item.description = textarea.val();
      logic.save_item(item, entityType);
    });

    btn_cancel.click(function() {
      var item = $("body").data("item");
      ui.cancel_item_edit(entityType, item);
    });
    $(".descr_detail").show();
    textarea.prop("readonly", false);
  },

  display_item_edit : function(entityType) {
    state.editing = !state.editing;
    console.debug($("#sel_set_obj").data("item"));
    ui.prepare_item_edit(entityType);

  },

  show_initial_domain : function(courses) {
    for ( var id in courses) {
      if (courses[id].externalCourseID == state.gid) {
        for ( var d in courses[id].domains) {
          logic.load(d, state.teacher);
          break;
        }
      }
    }
  },

  list_domains : function(courses) {
    $("#sel_set_dom").empty();
    for ( var id in courses) {
      if (courses[id].externalCourseID == "-1") {
        for ( var d in courses[id].domains) {
          courses[id].domains[d].id = d;
          $("#sel_set_dom").create("option", {
            value : JSON.stringify(courses[id].domains[d])
          }).create("txt", courses[id].domains[d].name);
        }
      } else {
        $("#sel_set_dom").create("option", {
          value : "-1"
        }).prop("disabled", true).create(
            "txt",
            "--- " + elgg.echo("wespot_fca:course") + " "
                + decodeURIComponent(courses[id].name) + " ---");
        for ( var d in courses[id].domains) {
          courses[id].domains[d].id = d;
          $("#sel_set_dom").create("option", {
            value : JSON.stringify(courses[id].domains[d])
          }).create("txt", "\u2192 " + courses[id].domains[d].name);
        }
      }
    }
    $("#sel_set_dom").prop("selectedIndex", "-1");
    $("#dia_set_dom").dialog("open");
    $(".item_description").empty();
  },

  display_lattice : function() {
    if (state.teacher) {
      lattice.init("#canvas_lattice", $(window).width() - 350, $(window)
          .height() - 100, "#div_lattice_info", backend);
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
      try {
        $("#dia_vis").dialogExtend("restore");
      } catch (error) {

      }
      $("#dia_vis").dialog("option", "title",
          elgg.echo('wespot_fca:lattice:tax') + " '" + state.domain.name + "'");
      $("#dia_vis").dialog("option", "width",
          $("#canvas_lattice").prop("width") + 240);
      $("#dia_vis").dialog("option", "height",
          $("#canvas_lattice").prop("height") + 50);
      try {
        $("#dia_vis").dialog("open").dialogExtend("restore");
      } catch (error) {
      }
      $("#dia_vis").fadeTo(0, 0);
      $("#dia_vis").fadeTo(1000, 1);
    } else {
      $("#dia_vis").show();
      $("#dia_vis").css("width", "100%");
      $("#dia_vis").css("height", $(window).height() - 150 + "px");
      lattice.init("#canvas_lattice", $("#dia_vis").width() - 220,
          $("#dia_vis").height(), "#div_lattice_info", backend);
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

      $("#dia_vis").fadeTo(0, 0);
      $("#dia_vis").fadeTo(1000, 1);
    }
  },
  resize : function() {
    // TODO this is a hack, but since elgg ships an ancient version on jQuery we
    // are limited to hacks
    if (state.teacher) {
      var d_state = $("#dia_vis").data("dialog-state");

      lattice.resize($(window).width() - 350, $(window).height() - 100);
      $("#dia_vis").dialog("option", "width",
          $("#canvas_lattice").prop("width") + 240);
      $("#dia_vis").dialog("option", "height",
          $("#canvas_lattice").prop("height") + 50);
      if (d_state == "minimized") {
        $("#dia_vis").dialogExtend("minimize");
      }
    } else {
      $("#dia_vis").css("height", $(window).height() - 150 + "px");
      lattice.resize($("#dia_vis").width() - 220, $("#dia_vis").height());
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
