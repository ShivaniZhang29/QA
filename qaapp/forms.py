from flask_wtf import FlaskForm
from wtforms import TextAreaField, SubmitField
from wtforms.validators import DataRequired

class QuestionForm(FlaskForm):
    question = TextAreaField('Your Question', validators=[DataRequired()])
    submit = SubmitField('Submit')
