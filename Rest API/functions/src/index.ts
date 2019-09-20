import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';
import * as express from 'express';
import * as bodyParser from "body-parser";

admin.initializeApp(functions.config().firebase);
const db = admin.firestore(); // Add this

const app = express();
const main = express();

main.use('/api/v1', app);
main.use(bodyParser.json());

export const webApi = functions.https.onRequest(main);

app.get('/warmup', (request, response) => {

    response.send('Warming up Data.');

});

app.post('/emails', async (request, response) => {
  try {
    const { name, title, message } = request.body;
    const data = {
      name,
      title,
      message
    } 
    const emailsRef = await db.collection('emails').add(data);
    const emails = await emailsRef.get();

    response.json({
      id: emailsRef.id,
      data: emails.data()
    });

  } catch(error){

    response.status(500).send(error);

  }
});

app.get('/emails/:id', async (request, response) => {
  try {
    const emailsId = request.params.id;

    if (!emailsId) throw new Error('emails ID is required');

    const emails = await db.collection('emails').doc(emailsId).get();

    if (!emails.exists){
        throw new Error('emails doesnt exist.')
    }

    response.json({
      id: emails.id,
      data: emails.data()
    });

  } catch(error){

    response.status(500).send(error);

  }
});

app.get('/emails', async (request, response) => {
  try {

    const emailsQuerySnapshot = await db.collection('emails').get();
    const emails = [];
    emailsQuerySnapshot.forEach(
        (doc) => {
            emails.push({
                id: doc.id,
                data: doc.data()
            });
        }
    );

    response.json(emails);

  } catch(error){

    response.status(500).send(error);

  }

});

app.put('/emails/:id', async (request, response) => {
  try {

    const emailsId = request.params.id;
    const title = request.body.title;

    if (!emailsId) throw new Error('id is blank');

    if (!title) throw new Error('Title is required');

    const data = { 
        title
    };
    const emailsRef = await db.collection('emails')
        .doc(emailsId)
        .set(data, { merge: true });

    response.json({
        id: emailsId,
        data
    })


  } catch(error){

    response.status(500).send(error);

  }

});

app.delete('/emails/:id', async (request, response) => {
  try {

    const emailsId = request.params.id;

    if (!emailsId) throw new Error('id is blank');

    await db.collection('emails')
        .doc(emailsId)
        .delete();

    response.json({
        id: emailsId,
    })


  } catch(error){

    response.status(500).send(error);

  }

});