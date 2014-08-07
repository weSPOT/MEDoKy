<?php
/**
 * Elgg tag input
 * Displays a tag input field
 *
 * @uses $vars['disabled']
 * @uses $vars['class']    Additional CSS class
 * @uses $vars['value']    Array of tags or a string
 * @uses $vars['entity']   Optional. Entity whose tags are being displayed (metadata ->tags)
 */
elgg_load_css ( 'medoky_tags' );
elgg_load_js ( 'jquery_create' );
elgg_load_js ( 'medoky_tags' );
if (isset ( $vars ['class'] )) {
  $vars ['class'] = "elgg-input-tags {$vars['class']}";
} else {
  $vars ['class'] = "elgg-input-tags";
}

$defaults = array (
  'value' => '',
  'disabled' => false 
);

if (isset ( $vars ['entity'] )) {
  $defaults ['value'] = $vars ['entity']->tags;
  unset ( $vars ['entity'] );
}

$vars = array_merge ( $defaults, $vars );

if (is_array ( $vars ['value'] )) {
  $tags = array ();
  
  foreach ( $vars ['value'] as $tag ) {
    if (is_string ( $tag )) {
      $tags [] = $tag;
    } else {
      $tags [] = $tag->value;
    }
  }
  
  $vars ['value'] = implode ( ", ", $tags );
}



$userId = elgg_get_logged_in_user_guid(); 
$inquiryId = elgg_get_page_owner_guid();
$response = file_get_contents('http://css-kti.tugraz.at/MEDoKyService/rest/getTagRecommendations/userId/'.$userId.'/courseId/'.$inquiryId);
echo "<script>console.log('http://css-kti.tugraz.at/MEDoKyService/rest/getTagRecommendations/userId/".$userId."/courseId/".$inquiryId."')</script>"; 
$response = json_decode($response);
$tags = $response->{'recommendations'};
$basedir = $CONFIG->url . "/mod/wespot_tags/";
?>

<div>
  <label for="medoky_tags_select">Add tag:</label> 
<? 
foreach($tags as $tag) {?>
<div class="medoky_tag" height="16px" width="16px" type="image" onclick="medoky_tags.select_tag(this)"><?php echo $tag;?></div>
<?php } ?>
</div>
<input type="text" id="input_tags" <?php echo elgg_format_attributes($vars); ?> />
