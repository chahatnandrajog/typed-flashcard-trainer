const API_BASE_URL = "http://localhost:8080/api";

let currentSessionId = null;

const flashcardForm = document.getElementById("flashcard-form");
const questionInput = document.getElementById("question");
const answerInput = document.getElementById("answer");
const createMessage = document.getElementById("create-message");
const startSessionBtn = document.getElementById("start-session-btn");
const studyArea = document.getElementById("study-area");
const studyQuestion = document.getElementById("study-question");
const studyAnswerInput = document.getElementById("study-answer");
const submitAnswerBtn = document.getElementById("submit-answer-btn");
const nextCardBtn = document.getElementById("next-card-btn");
const studyFeedback = document.getElementById("study-feedback");
const loadAnalyticsBtn = document.getElementById("load-analytics-btn");
const analyticsArea = document.getElementById("analytics-area");
const analyticsSummary = document.getElementById("analytics-summary");
const hardestCardsList = document.getElementById("hardest-cards-list");

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

startSessionBtn.addEventListener("click", async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/study-sessions/start`, {
      method: "POST"
    });
    if (!response.ok) {
      throw new Error("Failed to start study session");
    }

    const data = await response.json();

    currentSessionId = data.sessionId;

    studyArea.classList.remove("hidden");
    studyFeedback.textContent = "";
    studyAnswerInput.value = "";

    await loadNextCard();
  } catch (error) {
    console.error("Error starting study session:", error);
    studyFeedback.textContent = "Could not start study session.";
  }
}); 

async function loadNextCard() {
  try {
    const response = await fetch(
      `${API_BASE_URL}/study-sessions/${currentSessionId}/next`
    );

    if (!response.ok) {
      throw new Error("Failed to load next flashcard");
    }

    const data = await response.json();

    console.log("Next card response: ", data);

    if (data.completed) {
      studyQuestion.textContent = "Study session complete!";
      studyFeedback.textContent = "";
      studyAnswerInput.value = "";
      submitAnswerBtn.classList.add("hidden");
      nextCardBtn.classList.add("hidden");
      return;
    }

    studyQuestion.textContent = `#${data.id}: ${data.question}`;
    studyFeedback.textContent = "";
    studyAnswerInput.value = "";
    submitAnswerBtn.classList.remove("hidden");
    nextCardBtn.classList.add("hidden");
  } catch (error) {
    console.error("Error loading next flashcard:", error);
    studyFeedback.textContent = "Could not load next flashcard.";
  }
}

submitAnswerBtn.addEventListener("click", async () => {
  const userAnswer = studyAnswerInput.value.trim();

  if (!userAnswer) {
    studyFeedback.textContent = "Please enter an answer.";
    return;
  }

  try {
    const response = await fetch(
      `${API_BASE_URL}/study-sessions/${currentSessionId}/submit`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          userAnswer: userAnswer
        })
      }
    );

    if (!response.ok) {
      throw new Error("Failed to submit answer");
    }

    const data = await response.json();

    if (data.correct) {
      studyFeedback.textContent = "Correct!";
    } else {
      studyFeedback.textContent = `Incorrect. Correct answer: ${data.correctAnswer}`;
    }

    submitAnswerBtn.classList.add("hidden");
    nextCardBtn.classList.remove("hidden");
  } catch (error) {
    console.error("Error submitting answer:", error);
    studyFeedback.textContent = "Could not submit answer.";
  }
});

nextCardBtn.addEventListener("click", async () => {
  await loadNextCard();
});      

loadAnalyticsBtn.addEventListener("click", async () => {
  try {
    await loadAnalyticsSummary();
    await loadHardestCards();

    analyticsArea.classList.remove("hidden");
  } catch (error) {
    console.error("Error loading analytics:", error);
  }
});

async function loadAnalyticsSummary() {
  const response = await fetch(`${API_BASE_URL}/analytics/summary`);

  if (!response.ok) {
    throw new Error("Failed to load analytics summary");
  }

  const data = await response.json();

  analyticsSummary.innerHTML = `
    <p><strong>Total Flashcards:</strong> ${data.totalFlashcards}</p>
    <p><strong>Total Attempts:</strong> ${data.totalAttempts}</p>
    <p><strong>Correct Answers:</strong> ${data.totalCorrectAnswers}</p>
    <p><strong>Incorrect Answers:</strong> ${data.totalIncorrectAnswers}</p>
    <p><strong>Accuracy:</strong> ${data.accuracyPercentage.toFixed(2)}%</p>
  `;
} 

async function loadHardestCards() {
  const response = await fetch(`${API_BASE_URL}/analytics/hardest`);

  if (!response.ok) {
    throw new Error("Failed to load hardest cards");
  }

  const data = await response.json();

  hardestCardsList.innerHTML =  "";

  if (data.length === 0) {
    hardestCardsList.innerHTML = "<li>No attempts yet.</li>";
    return;
  }

  data.forEach(card => {
    const li = document.createElement("li");

    li.textContent = `${card.question} — Accuracy: ${card.accuracyPercentage.toFixed(2)}% (${card.incorrectCount} incorrect)`;

    hardestCardsList.appendChild(li);
  });
}