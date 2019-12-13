async function getComments(id) {
    const response = await fetch(`profile/post/${id}/comments"`);
    const comments = await response.json();
    console.log(JSON.stringify(comments));
}

