<script>
    import user from "../store/user.js";
    let username = '';
    let password = '';
    let currentError = null;

    const login = () => {
        fetch("http://localhost:8080/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username:username, password:password})
        })
            .then((res) => {
                if (res.status < 299) return res.json()
                if (res.status > 299) currentError = "Something wrong with server response";
            })
            .then((data) => {
                if (data) user.update(val => val = {...data})
            })
            .catch((err) => {
                currentError = err;
                console.log("Error login in: ", err)
            })
    }
</script>

<form on:submit|preventDefault={login}>
    <div>
        <label for="username">Username</label>
        <input type="text" id="username" bind:value={username}/>
    </div>
    <div>
        <label for="password">Password</label>
        <input type="password" id="password" bind:value={password}/>
    </div>
    <button type="submit">Log in</button>
</form>