# weSpot_MEDoKy_FCA - http://wespot.github.io/weSpot_MEDoKy_FCA/
MEDoKy (Modeling Inquiry relevant facets and Domain Knowledge) is a java based webservice component. 
It is used for modeling a learner's inquiry and domain relevant knowledge. 
The backend service is complemented by an elgg-plugins that allows for modeling and visualising of 
domain and learner models, recommending learning resources and recommending tags.

Get a local copy by using the following command:
git clone https://github.com/weSPOT/weSpot_MEDoKy_FCA

Three plugins are contained in the subfolder elgg-plugins. They are named wespot_fca, wespot_medoky and wespot_tags. 
wespot_fca is the GUI component of the FCA tool that is used for domain and learner modelling. 
wespot_medoky is the plugin for learning resource recommendations.
wespot_tags is the elgg extension to display tag recommendations.
"MEDoKyService" contains the java source code of the backend service. Subfolder in this directory include IO 
classes for communication with other components (REST API), the business logic, classes that retrieve log-data 
from a solr datamanagement system, an FCA unit with classes building the concept lattice and handling data from the 
elgg plugin and a classification package that will contain the learner model classes.
