import endpoints
import protorpc
from models import Question
from main import PARENT_KEY

@endpoints.api(name="questions", version="v1", description="Question API")
class QuestionsApi(protorpc.remote.Service):
    
    @Question.method(path="question/insert", name="question.insert", http_method="POST")
    def question_insert(self, request):
        if request.from_datastore: #field, not method
            my_question = request
        else:
            my_question = Question(parent=PARENT_KEY, content=request.content,
                               options=request.options, votes=request.votes, comments=request.comments,
                               valid_days=request.valid_days, question_type=request.question_type)
        my_question.put()
        return my_question
    
    
    @Question.query_method(name="question.list", path="question/list", http_method="GET", query_fields=("order", "limit", "pageToken"))
    def question_list(self, query):
        #We could modify the results if needed.
        return query


    @Question.method(name="question.delete", path="question/delete/{entityKey}", http_method="DELETE", request_fields=("entityKey",))
    def question_delete(self, request):
        if not request.from_datastore: # a field
            raise endpoints.NotFoundException("Can't delete since not in datastore")
        request.key.delete()
        return Question(content="deleted")

app = endpoints.api_server([QuestionsApi], restricted=False)