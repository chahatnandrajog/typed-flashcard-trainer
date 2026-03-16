const API_BASE_URL = "http://localhost:8080/api";

const flashcardForm = document.getElementById("flashcard-form");
const questionInput = document.getElementById("question");
const answerInput = document.getElementById("answer");
const createMessage = document.getElementById("create-message");

flashcardForm.addEventListener("submit", async (event) => {
  event.preventDefault();

  const question = questionInput.value.trim();
  const answer = answerInput.value.trim();

  if (!question || !answer) {
    createMessage.textContent = "Question and answer are required.";
    return;
  }

  try {
    const response = await fetch(`${API_BASE_URL}/flashcards`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        question: question,
        answer: answer
      })
    });

    console.log("Response status:", response.status);

    const responseText = await response.text();
    console.log("Raw response body:", responseText);

    if (!response.ok) {
      throw new Error(`Request failed with status ${response.status}`);
    }

    const savedFlashcard = JSON.parse(responseText);

    createMessage.textContent = `Flashcard saved with ID ${savedFlashcard.id}.`;
    flashcardForm.reset();
  } catch (error) {
    console.error("Error creating flashcard:", error);
    createMessage.textContent = `Could not save flashcard: ${error.message}`;
  }
});