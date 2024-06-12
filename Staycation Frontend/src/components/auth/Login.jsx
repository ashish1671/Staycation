import React, { useState } from 'react';
import {Link, useNavigate } from 'react-router-dom'; 
import axios from 'axios';

const Login = () => {
	const [credentials, setCredentials] = useState({ email: '', password: '' }); 
	const [errorMessage, setErrorMessage] = useState('');
	const navigate = useNavigate(); 

	
	const handleInputChange = (e) => {
		setCredentials({
			...credentials,
			[e.target.name]: e.target.value,
		});
	};

	

	
	const validateUser = async (credentials) => {
		const baseURL = 'http://localhost:9193'; 
		return await axios.post(`${baseURL}/users/login`, credentials);
	};
  
	
	const handleSubmit = async (e) => {
		e.preventDefault();
		if (credentials.email!== '' && credentials.password!== '') {
			try {
				const response = await validateUser(credentials);
				console.log(response.data.roles)
				console.log(response);
				if (response.data.status === "Logged in!") {
					localStorage.setItem('user', JSON.stringify(response.data));
					console.log('User data:', response.data);
					localStorage.setItem('userId', response.data.id);
					if (response.data.roles.some(role => role.name === 'ROLE_ADMIN')) 
						navigate('/admin');
					else
						navigate('/');
				}
				
 
			} catch (error) {
				console.log(error);
				setErrorMessage('Invalid username or password.');
				setCredentials({...credentials, password: "" });
			}
		} else {
			alert('All fields are required');
		}
	};
	
	
  return (
    <section className="container col-6 mt-5 mb-5">
      {errorMessage && <p className="alert alert-danger">{errorMessage}</p>}
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div className="row mb-3">
          <label htmlFor="email" className="col-sm-2 col-form-label">
            Email
          </label>
          <div>
            <input
              id="email"
              name="email"
              type="email"
              className="form-control"
              value={credentials.email}
              onChange={handleInputChange}
            />
          </div>
        </div>

        <div className="row mb-3">
          <label htmlFor="password" className="col-sm-2 col-form-label">
            Password
          </label>
          <div>
            <input
              id="password"
              name="password"
              type="password"
              className="form-control"
              value={credentials.password}
              onChange={handleInputChange}
            />
          </div>
        </div>

        <div className="mb-3">
          <button type="submit" className="btn btn-hotel" style={{ marginRight: "10px" }}>
            Login
          </button>
          <span style={{ marginLeft: "10px" }}>
            Do not have an account yet?<Link to={"/register"}> Register</Link>
          </span>
        </div>
      </form>
    </section>
  );
};

export default Login;
