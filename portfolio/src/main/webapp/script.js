// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  console.log("click to add random greeting!");
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

/**
 * Add a greeting from server
 */
async function addGreeting() {
    console.log("click to greet!");
    // fetch('/data').then(response => response.text()).then(queto => {
    //     document.getElementById('greeting-container').innerText = quote;
    // })
    const response = await fetch('/data');
    const quote = await response.text();
    document.getElementById('greeting-container').innerText = quote;
}

function getComment() {
  fetch('/data').then(response => response.json()).then((comment) => {
    // stats is an object, not a string, so we have to
    // reference its fields to create HTML content

    const commentListElement = document.getElementById('comment-container');
    commentListElement.innerHTML = '';
    commentListElement.appendChild(
        createListElement('Send Time: ' + comment.sendTime));
    commentListElement.appendChild(
        createListElement('Name: ' + comment.name));
    commentListElement.appendChild(
        createListElement('Comment: ' + comment.comment));
  });
}

/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

function getCommentList() {
  fetch('/check').then(response => response.json()).then((value) => {
    if (value.ifLoggedIn == 1){
      fetchBlobstoreUrlAndShowForm();
      fetch('/data').then(response => response.json()).then((comments) => {
        const MyComments = document.getElementById('comment-container');
        comments.forEach((value) => {
          console.log(value);
          MyComments.appendChild(createListElement("Send Time: " + value.sendTime ));
          MyComments.appendChild(createListElement("Name: " + value.name ));
          const commentText = document.createElement('text');
          commentText.innerText = ("Comment: " + value.comment);
          commentText.style.marginBottom = "20px";
          MyComments.appendChild(commentText);
          const imageElement = document.createElement("img");
          imageElement.src = value.imageUrl;
          MyComments.appendChild(imageElement);
        });
      });
    }
    else {
      window.alert(value.hint);
      window.location.href='/login';
      // const MyComments = document.getElementById('comment-container');
      // const hintText = document.createElement('text');
      // hintText.innerText = value.hint;
      // MyComments.appendChild(hintText);
    }
  });
}

function fetchBlobstoreUrlAndShowForm() {
  fetch('/blobstore-upload-url')
      .then((response) => {
      return response.text();
  })
  .then((imageUploadUrl) => {
    const messageForm = document.getElementById('my-form');
    console.log((imageUploadUrl));
    messageForm.action = imageUploadUrl;
    messageForm.classList.remove('hidden');
  });
}