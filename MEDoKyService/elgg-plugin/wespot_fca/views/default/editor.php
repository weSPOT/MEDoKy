<?php
$basedir = $CONFIG->url . "/mod/wespot_fca/";
?>
<link type="text/css" href="<?php echo $basedir; ?>css/smoothness/jquery-ui.css" rel="Stylesheet" />
<link type="text/css" href="<?php echo $basedir; ?>css/fca.css" rel="Stylesheet" />
<!--
<script src="<?php echo $basedir; ?>js/jquery-1.9.1.js"></script>
<script src="<?php echo $basedir; ?>js/jquery-ui.js"></script>
-->
<script src="<?php echo $basedir; ?>js/jquery-create.js"></script>
<script type="text/javascript" src="<?php echo $basedir; ?>js/fcatool.js"></script>
<script type="text/javascript" src="<?php echo $basedir; ?>js/arbor/arbor.js"></script>
<script type="text/javascript" src="<?php echo $basedir; ?>js/lattice.js"></script>
<script type="text/javascript" src="<?php echo $basedir; ?>js/jquery.dialogextend.js"></script>
<script>
$(function(){
logic.init("<?php echo $basedir; ?>","http://css-kmi.tugraz.at:8080/MEDoKyService/rest/FCATool/");
});
</script>
<table id="toolbar">
 <tr>
  <td class="toolbar"><input type="image" class="input" src="<?php echo $basedir; ?>img/new.svg"
   width="48px" height="48px" alt="New" title="<?php echo elgg_echo('wespot_fca:domain:new'); ?>"
   id="btn_new" onclick="document.location.reload(true)" /></td>
  <td class="toolbar"><input type="image" class="input" src="<?php echo $basedir; ?>img/open.svg"
   width="48px" height="48px" alt="Open" title="<?php echo elgg_echo('wespot_fca:domain:open'); ?>"
   id="btn_open" onclick="backend.get_domains(ui.list_domains)" /></td>
  <td class="toolbar"><input type="image" class="input" src="<?php echo $basedir; ?>img/save.svg"
   width="48px" height="48px" alt="Save" title="<?php echo elgg_echo('wespot_fca:domain:save'); ?>"
   id="btn_save" onclick="$('#dia_create_domain').dialog('open')" /></td>
  <td class="toolbar" style="width: 100%; vertical-align:middle;padding-left:2em"><h1 id="h_domain_name"></h1></td>
 </tr>
</table>
<hr />
<div id="main_table">
 <table id="input">
  <col>
  <col class="td_attr_0">
  <col class="td_attr_1">
  <col class="td_attr_2">
  <col class="td_attr_3">
  <col id="col_tail">

  <tr class="col_del">
   <td></td>
   <td class="right td_attr_0"><input type="image" src="<?php echo $basedir; ?>img/delete.svg"
    width="16px" height="16px" alt="x" title="<?php echo elgg_echo('wespot_fca:attr:rem'); ?>"
    id="btn_del_attr_0" class="input btn_del_attr" onclick="ui.rem_attribute(0)" /></td>
   <td class="right td_attr_1"><input type="image" src="<?php echo $basedir; ?>img/delete.svg"
    width="16px" height="16px" alt="x" title="<?php echo elgg_echo('wespot_fca:attr:rem'); ?>"
    id="btn_del_attr_1" class="input btn_del_attr" onclick="ui.rem_attribute(1)" /></td>
   <td class="right td_attr_2"><input type="image" src="<?php echo $basedir; ?>img/delete.svg"
    width="16px" height="16px" alt="x" title="<?php echo elgg_echo('wespot_fca:attr:rem'); ?>"
    id="btn_del_attr_2" class="input btn_del_attr" onclick="ui.rem_attribute(2)" /></td>
   <td class="right td_attr_3"><input type="image" src="<?php echo $basedir; ?>img/delete.svg"
    width="16px" height="16px" alt="x" title="<?php echo elgg_echo('wespot_fca:attr:rem'); ?>"
    id="btn_del_attr_3" class="input btn_del_attr" onclick="ui.rem_attribute(3)" /></td>
   <td class="tail"></td>
  </tr>
  <tr>
   <td class="obj_space"></td>
   <td class="td_attr td_attr_0"><input type="image" src="<?php echo $basedir; ?>img/left.svg"
    id="btn_move_left_0" width="16px" height="40px" alt="&lt;"
    title="<?php echo elgg_echo('wespot_fca:move_left'); ?>" class="input btn_move_left"
    onclick="ui.move_left(0)" /><input type="image" src="<?php echo $basedir; ?>img/right.svg"
    id="btn_move_right_0" width="16px" height="40px" alt="&gt;"
    title="<?php echo elgg_echo('wespot_fca:move_right'); ?>" class="input btn_move_right"
    onclick="ui.move_right(0)" /><input type="button" id="attr_0"
    class="input fullheight btn_attr col"
    value="<?php echo elgg_echo('wespot_fca:attr:dummy'); ?> 1" onclick="ui.set_item(0,1)" /></td>
   <td class="td_attr td_attr_1"><input type="image" src="<?php echo $basedir; ?>img/left.svg"
    id="btn_move_left_1" width="16px" height="40px" alt="&lt;" title="<?php echo elgg_echo('wespot_fca:move_left'); ?>"
    class="input btn_move_left" onclick="ui.move_left(1)" /><input type="image"
    src="<?php echo $basedir; ?>img/right.svg" id="btn_move_right_1" width="16px" height="40px"
    alt="&gt;" title="<?php echo elgg_echo('wespot_fca:move_right'); ?>"
    class="input btn_move_right" onclick="ui.move_right(1)" /><input type="button" id="attr_1"
    class="input fullheight btn_attr col"
    value="<?php echo elgg_echo('wespot_fca:attr:dummy'); ?> 2" onclick="ui.set_item(1,1)" /></td>
   <td class="td_attr td_attr_2"><input type="image" src="<?php echo $basedir; ?>img/left.svg"
    id="btn_move_left_2" width="16px" height="40px" alt="&lt;" title="<?php echo elgg_echo('wespot_fca:move_left'); ?>"
    class="input btn_move_left" onclick="ui.move_left(2)" /><input type="image"
    src="<?php echo $basedir; ?>img/right.svg" id="btn_move_right_2" width="16px" height="40px"
    alt="&gt;" title="<?php echo elgg_echo('wespot_fca:move_right'); ?>"
    class="input btn_move_right" onclick="ui.move_right(2)" /><input type="button" id="attr_2"
    class="input fullheight btn_attr col"
    value="<?php echo elgg_echo('wespot_fca:attr:dummy'); ?> 3" onclick="ui.set_item(2,1)" /></td>
   <td class="td_attr td_attr_3"><input type="image" src="<?php echo $basedir; ?>img/left.svg"
    id="btn_move_left_3" width="16px" height="40px" alt="&lt;" title="<?php echo elgg_echo('wespot_fca:move_left'); ?>"
    class="input btn_move_left" onclick="ui.move_left(3)" /><input type="image"
    src="<?php echo $basedir; ?>img/right.svg" id="btn_move_right_3" width="16px" height="40px"
    alt="&gt;" title="<?php echo elgg_echo('wespot_fca:move_right'); ?>"
    class="input btn_move_right" onclick="ui.move_right(3)" /><input type="button" id="attr_3"
    class="input fullheight btn_attr col"
    value="<?php echo elgg_echo('wespot_fca:attr:dummy'); ?> 4" onclick="ui.set_item(3,1)" /></td>
   <td class="tail" style="background-color: #fff; vertical-alignment: middle"><input type="image"
    class="input" style="border: none; padding-left: 10px;"
    src="<?php echo $basedir; ?>img/plus.svg" width="16px" height="16px" alt="+"
    title="<?php echo elgg_echo('wespot_fca:attr:add'); ?>" onclick="ui.append_attribute()" /><?php echo elgg_echo('wespot_fca:attr:add'); ?></td>
  </tr>
  <tr id="tr_obj_0" class="tr_obj">
   <td class="left"><input type="image" src="<?php echo $basedir; ?>img/up.svg" width="40px"
    height="16px" alt="^" title="<?php echo elgg_echo('wespot_fca:move_up'); ?>" id="btn_move_up_0"
    class="input btn_move_up" onclick="ui.move_up(0)" /> <input type="image"
    src="<?php echo $basedir; ?>img/down.svg" width="40px" height="16px" alt="v"
    title="<?php echo elgg_echo('wespot_fca:move_down'); ?>" id="btn_move_down_0"
    class="input btn_move_down" onclick="ui.move_down(0)" /> <input type="image"
    src="<?php echo $basedir; ?>img/delete.svg" width="16px" height="16px" alt="x"
    title="<?php echo elgg_echo('wespot_fca:obj:rem'); ?>" id="btn_del_obj_0"
    class="input btn_del_obj" onclick="ui.rem_object(0)" /> <input type="button" id="obj_0"
    class="input btn_obj" value="<?php echo elgg_echo('wespot_fca:obj:dummy'); ?> 1"
    onclick="ui.set_item(0,0)" /></td>
   <td class="td_attr_0"><input type="checkbox" class="input check" id="obj_0_attr_0" /></td>
   <td class="td_attr_1"><input type="checkbox" class="input check" id="obj_0_attr_1" /></td>
   <td class="td_attr_2"><input type="checkbox" class="input check" id="obj_0_attr_2" /></td>
   <td class="td_attr_3"><input type="checkbox" class="input check" id="obj_0_attr_3" /></td>
   <td class="tail" style="background-color: #fff"></td>

  </tr>
  <tr id="tr_obj_1" class="tr_obj">
   <td class="left"><input type="image" src="<?php echo $basedir; ?>img/up.svg" width="40px"
    height="16px" alt="^" title="<?php echo elgg_echo('wespot_fca:move_up'); ?>" id="btn_move_up_1"
    class="input btn_move_up" onclick="ui.move_up(1)" /> <input type="image"
    src="<?php echo $basedir; ?>img/down.svg" width="40px" height="16px" alt="v"
    title="<?php echo elgg_echo('wespot_fca:move_down'); ?>" id="btn_move_down_1"
    class="input btn_move_down" onclick="ui.move_down(1)" /> <input type="image"
    src="<?php echo $basedir; ?>img/delete.svg" width="16px" height="16px" alt="x"
    title="<?php echo elgg_echo('wespot_fca:obj:rem'); ?>" id="btn_del_obj_1"
    class="input btn_del_obj" onclick="ui.rem_object(1)" /> <input type="button" id="obj_1"
    class="input btn_obj" value="<?php echo elgg_echo('wespot_fca:obj:dummy'); ?> 2"
    onclick="ui.set_item(1,0)" /></td>
   <td class="td_attr_0"><input type="checkbox" class="input check" id="obj_1_attr_0" /></td>
   <td class="td_attr_1"><input type="checkbox" class="input check" id="obj_1_attr_1" /></td>
   <td class="td_attr_2"><input type="checkbox" class="input check" id="obj_1_attr_2" /></td>
   <td class="td_attr_3"><input type="checkbox" class="input check" id="obj_1_attr_3" /></td>
   <td class="tail" style="background-color: #fff"></td>
  </tr>
  <tr id="tr_obj_2" class="tr_obj">
   <td class="left"><input type="image" src="<?php echo $basedir; ?>img/up.svg" width="40px"
    height="16px" alt="^" title="<?php echo elgg_echo('wespot_fca:move_up'); ?>" id="btn_move_up_2"
    class="input btn_move_up" onclick="ui.move_up(2)" /> <input type="image"
    src="<?php echo $basedir; ?>img/down.svg" width="40px" height="16px" alt="v"
    title="<?php echo elgg_echo('wespot_fca:move_down'); ?>" id="btn_move_down_2"
    class="input btn_move_down" onclick="ui.move_down(2)" /> <input type="image"
    src="<?php echo $basedir; ?>img/delete.svg" width="16px" height="16px" alt="x"
    title="<?php echo elgg_echo('wespot_fca:obj:rem'); ?>" id="btn_del_obj_2"
    class="input btn_del_obj" onclick="ui.rem_object(2)" /><input type="button" id="obj_2"
    class="input btn_obj" value="<?php echo elgg_echo('wespot_fca:obj:dummy'); ?> 3"
    onclick="ui.set_item(2,0)" /></td>
   <td class="td_attr_0"><input type="checkbox" class="input check" id="obj_2_attr_0" /></td>
   <td class="td_attr_1"><input type="checkbox" class="input check" id="obj_2_attr_1" /></td>
   <td class="td_attr_2"><input type="checkbox" class="input check" id="obj_2_attr_2" /></td>
   <td class="td_attr_3"><input type="checkbox" class="input check" id="obj_2_attr_3" /></td>
   <td class="tail" style="background-color: #fff"></td>
  </tr>
  <tr id="tr_obj_3" class="tr_obj">
   <td class="left"><input type="image" src="<?php echo $basedir; ?>img/up.svg" width="40px"
    height="16px" alt="^" title="<?php echo elgg_echo('wespot_fca:move_up'); ?>" id="btn_move_up_3"
    class="input btn_move_up" onclick="ui.move_up(3)" /> <input type="image"
    src="<?php echo $basedir; ?>img/down.svg" width="40px" height="16px" alt="v"
    title="<?php echo elgg_echo('wespot_fca:move_down'); ?>" id="btn_move_down_3"
    class="input btn_move_down" onclick="ui.move_down(3)" /> <input type="image"
    src="<?php echo $basedir; ?>img/delete.svg" width="16px" height="16px" alt="x"
    title="<?php echo elgg_echo('wespot_fca:obj:rem'); ?>" id="btn_del_obj_3"
    class="input btn_del_obj" onclick="ui.rem_object(3)" /><input type="button" id="obj_3"
    class="input btn_obj" value="<?php echo elgg_echo('wespot_fca:obj:dummy'); ?> 4"
    onclick="ui.set_item(3,0)" /></td>
   <td class="td_attr_0"><input type="checkbox" class="input check" id="obj_3_attr_0" /></td>
   <td class="td_attr_1"><input type="checkbox" class="input check" id="obj_3_attr_1" /></td>
   <td class="td_attr_2"><input type="checkbox" class="input check" id="obj_3_attr_2" /></td>
   <td class="td_attr_3"><input type="checkbox" class="input check" id="obj_3_attr_3" /></td>
   <td class="tail" style="background-color: #fff"></td>
  </tr>
  <tr id="tr_obj_4" class="tr_obj">
   <td class="left"><input type="image" src="<?php echo $basedir; ?>img/up.svg" width="40px"
    height="16px" alt="^" title="<?php echo elgg_echo('wespot_fca:move_up'); ?>" id="btn_move_up_4"
    class="input btn_move_up" onclick="ui.move_up(4)" /> <input type="image"
    src="<?php echo $basedir; ?>img/down.svg" width="40px" height="16px" alt="v"
    title="<?php echo elgg_echo('wespot_fca:move_down'); ?>" id="btn_move_down_4"
    class="input btn_move_down" onclick="ui.move_down(4)" /> <input type="image"
    src="<?php echo $basedir; ?>img/delete.svg" width="16px" height="16px" alt="x"
    title="<?php echo elgg_echo('wespot_fca:obj:rem'); ?>" id="btn_del_obj_4"
    class="input btn_del_obj" onclick="ui.rem_object(4)" /><input type="button" id="obj_4"
    class="input btn_obj" value="<?php echo elgg_echo('wespot_fca:obj:dummy'); ?> 5"
    onclick="ui.set_item(4,0)" /></td>
   <td class="td_attr_0"><input type="checkbox" class="input check" id="obj_4_attr_0" /></td>
   <td class="td_attr_1"><input type="checkbox" class="input check" id="obj_4_attr_1" /></td>
   <td class="td_attr_2"><input type="checkbox" class="input check" id="obj_4_attr_2" /></td>
   <td class="td_attr_3"><input type="checkbox" class="input check" id="obj_4_attr_3" /></td>
   <td class="tail" style="background-color: #fff"></td>
  </tr>
  <tr class="obj_tail">
   <td class="center"><input type="image" class="input" style="border: none"
    src="<?php echo $basedir; ?>img/plus.svg" width="16px" height="16px" alt="+"
    title="<?php echo elgg_echo('wespot_fca:obj:add'); ?>" id="btn_obj_add" class="fixed_height"
    onclick="ui.append_object()" /> <?php echo elgg_echo('wespot_fca:obj:add'); ?> </td>
   <td class="td_attr_0"></td>
   <td class="td_attr_1"></td>
   <td class="td_attr_2"></td>
   <td class="td_attr_3"></td>
   <td class="tail" style="background-color: #fff"></td>
  </tr>
 </table>
</div>


<div id="dia_set_obj" title="<?php echo elgg_echo('wespot_fca:obj:set'); ?>">
 <div id="dia_set_obj_content">
  <table>
   <tr>
    <td class="layout_select_name"><?php echo elgg_echo('wespot_fca:obj:sel'); ?></td>
    <td><select id="sel_set_obj" class="sel_set" onchange="ui.display_item_description(this, 0)"></select></td>
    <td><input type="image" class="btn_edit" src="<?php echo $basedir; ?>img/edit.svg" width="24px"
     height="24px"
     onclick="ui.display_item_edit(document.getElementById('sel_set_obj'),document.getElementById('text_descr_obj'),0)" /></td>
   </tr>
   <tr class="descr_detail">
    <td class="layout_select"><?php echo elgg_echo('wespot_fca:description'); ?></td>
    <td><textarea id="text_descr_obj" rows="5" cols="35" class="text_description"></textarea></td>
    <td></td>
   </tr>
   <tr class="descr_detail">
    <td class="layout_select"><?php echo elgg_echo('wespot_fca:l_objs'); ?></td>
    <td>
     <div id="lo_obj" class="div_lo"></div>
    </td>
    <td></td>
   </tr>
  </table>
 </div>
 <div class="choice">
  <hr>
  <input id="btn_choose_obj_cancel" type="button" class="input_pad"
   value="<?php echo elgg_echo('wespot_fca:cancel'); ?>" onclick="$('#dia_set_obj').dialog('close')" />
  <input id="btn_choose_obj_ok" class="input_pad" type="button"
   value="<?php echo elgg_echo('wespot_fca:ok'); ?>" onclick="logic.choose_object()" />
 </div>
</div>



<div id="dia_set_attr" title="<?php echo elgg_echo('wespot_fca:attr:set'); ?>">
 <div id="dia_set_attr_content">
  <table>
   <tr>
    <td class="layout_select_name"><?php echo elgg_echo('wespot_fca:attr:sel'); ?></td>
    <td><select id="sel_set_attr" class="sel_set" onchange="ui.display_item_description(this, 1)"></select></td>
    <td><input type="image" class="btn_edit" src="<?php echo $basedir; ?>img/edit.svg" width="24px"
     height="24px"
     onclick="ui.display_item_edit(document.getElementById('sel_set_attr'),document.getElementById('text_descr_attr'),1)" /></td>
   </tr>
   <tr class="descr_detail">
    <td class="layout_select"><?php echo elgg_echo('wespot_fca:description'); ?></td>
    <td><textarea id="text_descr_attr" rows="5" cols="35" class="text_description"></textarea></td>
    <td></td>
   </tr>
   <tr class="descr_detail">
    <td class="layout_select"><?php echo elgg_echo('wespot_fca:l_objs'); ?></td>
    <td>
     <div id="lo_attr" class="div_lo"></div>
    </td>
    <td></td>
   </tr>
  </table>
 </div>
 <div class="choice">
  <hr>
  <input id="btn_choose_attr_cancel" type="button" class="input_pad"
   value="<?php echo elgg_echo('wespot_fca:cancel'); ?>"
   onclick="$('#dia_set_attr').dialog('close')" /> <input id="btn_choose_attr_ok" class="input_pad"
   type="button" value="<?php echo elgg_echo('wespot_fca:ok'); ?>"
   onclick="logic.choose_attribute()" />
 </div>
</div>

<div id="dia_set_lo" title="<?php echo elgg_echo('wespot_fca:l_objs:add'); ?>">
 <div id="dia_set_lo_content">
  <?php echo elgg_echo('wespot_fca:l_obj:sel'); ?><select id="sel_set_lo" class="select_basic"
   onchange="ui.display_description(this, 0)"></select>
  <p class="item_description"></p>
 </div>
 <hr>
 <div class="choice">
  <input id="btn_choose_lo_cancel" type="button" class="input_pad"
   value="<?php echo elgg_echo('wespot_fca:cancel'); ?>" onclick="$('#dia_set_lo').dialog('close')" />
  <input id="btn_choose_lo_ok" class="input_pad" type="button"
   value="<?php echo elgg_echo('wespot_fca:ok'); ?>" onclick="logic.set_lo()" />
 </div>
</div>

<div id="dia_set_dom" title="<?php echo elgg_echo('wespot_fca:domain:open'); ?>">
 <div id="dia_set_dom_content">
  <?php echo elgg_echo('wespot_fca:domain:sel'); ?><select id="sel_set_dom" class="select_basic"
   onchange="ui.display_description(this, 2)"></select>
  <p class="item_description"></p>
 </div>
 <div class="choice">
  <hr>
  <input id="btn_choose_dom_cancel" type="button" class="input_pad"
   value="<?php echo elgg_echo('wespot_fca:cancel'); ?>" onclick="$('#dia_set_dom').dialog('close')" />
  <input id="btn_choose_dom_ok" class="input_pad" class="input_pad" type="button"
   value="<?php echo elgg_echo('wespot_fca:ok'); ?>"
   onclick="logic.load(JSON.parse($('#sel_set_dom').get(0).options[$('#sel_set_dom').get(0).selectedIndex].value).id)" />
 </div>
</div>
<div id="dia_rem_obj" title="<?php echo elgg_echo('wespot_fca:obj:rem'); ?>">
 <p id="dia_rem_obj_content">
  Are you sure that you want to delete the object '<span id="span_rem_obj"></span>'?.
 </p>
 <div class="choice">
  <hr>
  <input id="btn_rem_obj_no" type="button" class="input_pad" value="No"
   onclick="$('#dia_rem_obj').dialog('close')" /> <input id="btn_rem_obj_yes" type="button"
   class="input_pad" value="Yes" />
 </div>
</div>

<div id="dia_rem_attr" title="<?php echo elgg_echo('wespot_fca:attr:rem'); ?>">
 <p id="dia_rem_attr_content">
  Are you sure that you want to remove the attribute '<span id="span_rem_attr"></span>'?
 </p>
 <div class="choice">
  <hr>
  <input id="btn_rem_attr_no" type="button" class="input_pad" value="No"
   onclick="$('#dia_rem_attr').dialog('close')" /> <input id="btn_rem_attr_yes" type="button"
   class="input_pad" value="Yes" />
 </div>
</div>

<div id="dia_create_obj" title="Create New Object">
 <table>
  <tr>
   <td><?php echo elgg_echo('wespot_fca:name'); ?>:</td>
   <td><input type="text" id="input_create_obj_name" /></td>
  </tr>
  <tr>
   <td><?php echo elgg_echo('wespot_fca:description'); ?>:</td>
   <td><input type="text" id="input_create_obj_description" /></td>
  </tr>
 </table>
 <div class="choice">
  <hr>
  <input id="btn_create_obj_cancel" type="button" class="input_pad"
   value="<?php echo elgg_echo('wespot_fca:cancel'); ?>" onclick="ui.set_item(state.obj_index,0)" />
  <input id="btn_create_obj_ok" class="input_pad" type="button"
   value="<?php echo elgg_echo('wespot_fca:ok'); ?>"
   onclick="logic.create_object($('#input_create_obj_name').prop('value'),$('#input_create_obj_description').prop('value'))" />
 </div>
</div>

<div id="dia_create_attr" title="Create New Attribute">
 <table>
  <tr>
   <td><?php echo elgg_echo('wespot_fca:name'); ?>:</td>
   <td><input type="text" id="input_create_attr_name" /></td>
  </tr>
  <tr>
   <td><?php echo elgg_echo('wespot_fca:description'); ?>:</td>
   <td><input type="text" id="input_create_attr_description" /></td>
  </tr>
 </table>
 <div class="choice">
  <hr>
  <input id="btn_create_attr_cancel" type="button" class="input_pad"
   value="<?php echo elgg_echo('wespot_fca:cancel'); ?>" onclick="ui.set_item(state.attr_index,1)" />
  <input id="btn_create_attr_ok" class="input_pad" type="button"
   value="<?php echo elgg_echo('wespot_fca:ok'); ?>"
   onclick="logic.create_attribute($('#input_create_attr_name').prop('value'),$('#input_create_attr_description').prop('value'))" />
 </div>
</div>

<div id="dia_create_domain" title="Create New Domain">
 <table>
  <tr>
   <td><?php echo elgg_echo('wespot_fca:name'); ?>:</td>
   <td><input type="text" id="input_create_domain_name" /></td>
  </tr>
  <tr>
   <td><?php echo elgg_echo('wespot_fca:description'); ?>:</td>
   <td><input type="text" id="input_create_domain_description" /></td>
  </tr>
 </table>
 <div class="choice">
  <hr>
  <input id="btn_create_domain_cancel" type="button" class="input_pad"
   value="<?php echo elgg_echo('wespot_fca:cancel'); ?>"
   onclick="$('#dia_create_domain').dialog('close')" /> <input id="btn_create_domain_ok"
   type="button" class="input_pad" value="<?php echo elgg_echo('wespot_fca:ok'); ?>"
   onclick="logic.create_domain($('#input_create_domain_name').prop('value'),$('#input_create_domain_description').prop('value'))" />
 </div>
</div>

<div id="dia_create_lo" title="Create New Learning Object">
 <table>
  <tr>
   <td><?php echo elgg_echo('wespot_fca:name'); ?>:</td>
   <td><input type="text" id="input_create_lo_name" /></td>
  </tr>
  <tr>
   <td>Url:</td>
   <td><input type="text" id="input_create_lo_description" /></td>
  </tr>
 </table>
 <div class="choice">
  <hr>
  <input id="btn_create_lo_cancel" type="button" class="input_pad"
   value="<?php echo elgg_echo('wespot_fca:cancel'); ?>"
   onclick="$('#dia_create_lo').dialog('close')" /> <input id="btn_create_lo_ok" type="button"
   class="input_pad" value="<?php echo elgg_echo('wespot_fca:ok'); ?>"
   onclick="logic.create_lo($('#input_create_lo_name').prop('value'),'',$('#input_create_lo_description').prop('value'))" />
 </div>
</div>


<div id="dia_vis" title="">
 <img src="<?php echo $basedir; ?>img/loading.gif" id="vis_loading" />
 <table>
  <tr>
   <td><span id="span_latticeview" style="position: absolute; text-align: left; left: 0; top: 0; padding: 3px"> <input
     type="checkbox" id="cb_latticeview" style="width: 20px" onclick="lattice.switch_view()" /> <?php echo elgg_echo('wespot_fca:lattice:show_full'); ?>
   </span></td>
   <td>
    <div id="div_lattice_vis">
     <canvas id="canvas_lattice">
     </canvas>
    </div>
   </td>
   <td>
    <div id="div_lattice_info"></div>
   </td>
  </tr>
 </table>


</div>
<!--[if lt IE 7 ]>  <div class="msie_fca"></div> <![endif]-->
<!--[if IE 7 ]>     <div class="msie_fca"></div> <![endif]-->
<!--[if IE 8 ]>     <div class="msie_fca"></div> <![endif]-->
<!--[if IE 9 ]>     <div class="msie_fca"></div> <![endif]-->
