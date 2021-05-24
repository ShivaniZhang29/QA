from qaapp import db

class QA(db.Model):
    id = db.Column(db.Integer, primary_key = True)
    question = db.Column(db.String, unique=True)
    answer = db.Column(db.String)
