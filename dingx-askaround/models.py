from endpoints_proto_datastore.ndb.model import EndpointsModel
from google.appengine.ext import ndb
from itertools import repeat

class Question(EndpointsModel):
    _message_fields_schema = ("entityKey", 
                              "content", 
                              "options", 
                              "votes", 
                              "comments", 
                              "create_date", 
                              "valid_days",
                              "question_type")
    content = ndb.StringProperty()
    options = ndb.StringProperty(repeated=True)
    votes = ndb.IntegerProperty(repeated=True)
    comments = ndb.StringProperty(repeated=True)
    create_date = ndb.DateTimeProperty(auto_now_add=True)
    valid_days = ndb.IntegerProperty()
    question_type=ndb.StringProperty()
    
