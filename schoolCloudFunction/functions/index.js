const functions = require('firebase-functions');

const admin = require('firebase-admin');

admin.initializeApp();

// Create and Deploy Your First Cloud Functions
// https://firebase.google.com/docs/functions/write-firebase-functions

exports.helloWorld = functions.https.onRequest((request, response) => {
 response.send("Hello from Firebase!");
});

exports.sendNotification = functions.database.ref('/notification/{userId}/{pushId}')
        .onWrite((change, context) => {
            const userId = context.params.userId;
            const obtained = change.after.val();
            console.log(userId);
            const message = {
                notification: {
                    title: obtained.title,
                    body: obtained.body,
                    sound: "default",
                }
            };

            return admin.messaging().sendToTopic(userId, message);
        });