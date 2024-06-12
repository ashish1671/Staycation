import React, { useEffect, useState } from "react"
import { deleteUser,  getUser } from "../utils/ApiFunctions"
import { useNavigate } from "react-router-dom"


const Profile = () => {
  const [user, setUser] = useState({
    id: "",
    email: "",
    firstName: "",
    lastName: "",
    roles: [{ id: "", name: "" }]
  })

  const [message, setMessage] = useState("")
  const [errorMessage, setErrorMessage] = useState("")
  const navigate = useNavigate()
  const userId = localStorage.getItem("userId")


  useEffect(() => {
	const fetchUser = async () => {
		if (userId) {
			try {
				const userData = await getUser(userId);
				setUser(userData);
			} catch (error) {
				console.error(error);
			}
		} else {
			console.error('User ID must not be null');
		}
	}
  
	fetchUser();
  }, [userId]);
  



  const handleDeleteAccount = async () => {
    const confirmed = window.confirm(
      "Are you sure you want to delete your account? This action cannot be undone."
    )
    if (confirmed) {
      await deleteUser(userId)
        .then((response) => {
          setMessage(response.data)
          localStorage.removeItem("userId")
          localStorage.removeItem("userRole")
          navigate("/")
          window.location.reload()
        })
        .catch((error) => {
          setErrorMessage(error.data)
        })
    }
  }

  return (
    <div className="container">
      {errorMessage && <p className="text-danger">{errorMessage}</p>}
      {message && <p className="text-danger">{message}</p>}
      {user ? (
        <div className="card p-5 mt-5" style={{ backgroundColor: "whitesmoke" }}>
          <h4 className="card-title text-center">User Information</h4>
          <div className="card-body">
            <div className="col-md-10 mx-auto">
              <div className="card mb-3 shadow">
                <div className="row g-0">
                  <div className="col-md-2">
                    <div className="d-flex justify-content-center align-items-center mb-4">
                    
                    </div>
                  </div>

                  <div className="col-md-10">
                    <div className="card-body">
                      <div className="form-group row">
                        <label className="col-md-2 col-form-label fw-bold">ID:</label>
                        <div className="col-md-10">
                          <p className="card-text">{user.id}</p>
                        </div>
                      </div>
                      <hr />

                      <div className="form-group row">
                        <label className="col-md-2 col-form-label fw-bold">First Name:</label>
                        <div className="col-md-10">
                          <p className="card-text">{user.firstName}</p>
                        </div>
                      </div>
                      <hr />

                      <div className="form-group row">
                        <label className="col-md-2 col-form-label fw-bold">Last Name:</label>
                        <div className="col-md-10">
                          <p className="card-text">{user.lastName}</p>
                        </div>
                      </div>
                      <hr />

                      <div className="form-group row">
                        <label className="col-md-2 col-form-label fw-bold">Email:</label>
                        <div className="col-md-10">
                          <p className="card-text">{user.email}</p>
                        </div>
                      </div>
                      <hr />
                      <div className="form-group row">
                        <label className="col-md-2 col-form-label fw-bold">phno:</label>
                        <div className="col-md-10">
                          <p className="card-text">{user.phno}</p>
                        </div>
                      </div>
                      <hr />

                      <div className="form-group row">
                        <label className="col-md-2 col-form-label fw-bold">Roles:</label>
                        <div className="col-md-10">
                          {user.roles.some(role => role.name === 'ROLE_ADMIN') ? (
                            <p className="card-text">Admin</p>
                          ) : (
                            <p className="card-text">User</p>
                          )}
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              
              <div className="d-flex justify-content-center">
                <div className="mx-2">
                  <button className="btn btn-danger btn-sm" onClick={handleDeleteAccount}>
                    Close account
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      ) : (
        <p>Loading user data...</p>
      )}
    </div>
  )
}

export default Profile
