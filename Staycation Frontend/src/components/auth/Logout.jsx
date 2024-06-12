import React from "react"
import { Link} from "react-router-dom"
import { useHistory } from "react-router-dom";

const Logout = () => {
	const history = useHistory();

	const handleLogout = () => {
		localStorage.removeItem("userId"); 
		history.push("/login"); 
	};

	return (
		<>
			<li>
				<Link className="dropdown-item" to={"/profile"}>
					Profile
				</Link>
			</li>
			<li>
				<hr className="dropdown-divider" />
			</li>
			<button className="dropdown-item" onClick={handleLogout}>
				Logout
			</button>
		</>
	)
}

export default Logout
