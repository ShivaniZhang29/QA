from qaapp import app, db, Config
from flask import render_template, flash, redirect, url_for, session, request, jsonify
from qaapp.forms import QuestionForm
from qaapp.models import QA
import wolframalpha

@app.route('/')
@app.route('/question', methods=['GET','POST'])
def question():
    form = QuestionForm()
    app_id = 'JV3K2Y-58RKKEHW5V'
    client = wolframalpha.Client(app_id)
    if form.validate_on_submit():
        ques = form.question.data
        print(ques)
        res = client.query(form.question)
        # Includes only text from the response
        ans = next(res.results).text
        print(ans)
        return redirect(url_for('answer', ans=ans, ques = ques))
    return render_template('question.html', title='Question',form=form)

@app.route('/answer')
def answer():
    return render_template('answer.html')
