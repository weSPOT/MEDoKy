medoky_tags = {
  test : function() {
    console.debug("Tag JS Working");
  },
  select_tag : function(select) {
    var tag = select.textContent;
    tag.trim();	
    
    var tags = ($("#input_tags").val()).split(", ");
    if (tags.indexOf(tag)==-1){
	$("#input_tags").val($("#input_tags").val() + ", " + tag);
    	if ($("#input_tags").val().indexOf(",") == 0)
          $("#input_tags").val($("#input_tags").val().substring(1));
    }	
  },
}
