(function () {
    const constants = {
        QUESTION_PLACE: 0,
        ANSWERS_PLACE: 1,
        SC_OK: 200,
        SC_BAD_REQUEST: 400,
        SC_FORBIDDEN: 403,
    };

    function displayPollAnswers(json) {
        let question = document.getElementById("pollQuestion");

        question.innerText = json[constants.QUESTION_PLACE];

        let answers = document.getElementById("answers");
        for (let i = constants.ANSWERS_PLACE; i < json.length; i++) {
            answers.innerHTML += `<li class="list-group-item">
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="optionsRadios" value="${json[i]}">
                                        ${json[i]}  
                                    </label>
                                </div>
                            </li>
        `;
        }
    }

    //******************************************************
    function displayPollVotes(json) {
        let votes = document.getElementById("votes");
        votes.innerText = "";
        for (let i = 0; i < json.length; i++) {
            votes.innerHTML += ` <li class="list-group-item">
                                <div class="radio">
                                    <label>
                                        ${json[i]}
                                    </label>
                                </div>
                            </li>
        `;
        }
    }

    //******************************************************

    // takes an array
    function hideElements(ids) {
        for (let id of ids)
            document.getElementById(`${id}`).classList.add('d-none');
    }

    // takes an array
    function revealElements(ids) {
        for (let id of ids)
            document.getElementById(`${id}`).classList.remove('d-none');
    }

    //******************************************************

    function status(response) {
        if (response.status >= 200 && response.status < 300) {
            return Promise.resolve(response);
        } else {
            return Promise.reject(new Error(response.statusText));
        }
    }

    //******************************************************

    async function fetchQuestionAnswers() {
        await fetch("/api/getPoll", {method: "GET"})
            .then(status)
            .then(res => res.json())
            .then(json => {
                displayPollAnswers(json);
            })
            .catch(function (err) {
                return Promise.reject(new Error(err.statusText));
            });
    }

    //******************************************************


    async function fetchVotes() {
        await fetch("/api/vote", {method: "GET"})
            .then(res => res.json())
            .then(json => {
                displayPollVotes(json);
            })
            .catch(function (err) {
                return Promise.reject(new Error(err.statusText));
            });
    }

    //******************************************************

    document.addEventListener('DOMContentLoaded', () => { // wait for page to load

        fetchQuestionAnswers()
            .then(fetchVotes)
            .catch((function () {
                revealElements(['pollError']);
                hideElements(['noChoice', 'inputForm', 'results', 'invalidVote']);
            }));

        let form = document.getElementById('inputForm');
        form.addEventListener('submit', submitForm);
    });

    //******************************************************

    function handleStatus(status) {
        switch (status) {
            case constants.SC_OK:
                hideElements(['invalidVote', 'pollError', 'noChoice']);
                break;
            case constants.SC_BAD_REQUEST:
                revealElements(['noChoice']);
                hideElements(['invalidVote', 'pollError']);
                break;
            case constants.SC_FORBIDDEN:
                revealElements(['invalidVote']);
                hideElements(['noChoice', 'pollError']);
                break;
        }
    }

    //******************************************************

    const submitForm = async function (event) {
        event.preventDefault();

        let selectedAnswer = "";
        const answers = document.querySelectorAll('input[name="optionsRadios"]');
        for (const answer of answers) {
            if (answer.checked) {
                selectedAnswer = answer.value;
                break;
            }
        }

        await fetch("/api/vote", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'datatype': 'json'
            },
            body: new URLSearchParams({answer: selectedAnswer}).toString()
        }).then((res) => {
            handleStatus(res.status);
        })
            .then(fetchVotes)
            .catch(function () {
                revealElements(['pollError']);
                hideElements(['noChoice', 'invalidVote']);
            });
    };

})();


