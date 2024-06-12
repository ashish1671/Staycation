import React from "react"
import MainHeader from "../layout/MainHeader"

import RoomSearch from "../common/RoomSearch"

const Home = () => {
	
  

	return (
		<section>
			
			<MainHeader />
			<div className="container">
				<RoomSearch />
			</div>
		</section>
	)
}

export default Home
