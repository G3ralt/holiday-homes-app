import React, { Component } from 'react'
import adminData from "../facades/adminFacade";

class AdminPage extends Component {

  constructor() {
    super();
    this.state = { data: null, err: "" };
  }

  componentWillMount() {
    /*
    This will fetch data each time you navigate to this route
    If only required once, add "logic" to determine when data should be "refetched"
    */
    adminData.getData((e, data) => {
      if (e) {
        return this.setState({ err: e.err })
      }
      this.setState({ err: "", data });
  });
}

  mapData = (a) => {
	console.log(a);
	var html = ""
	if(a === null){
		return "";
	}

	var rows = a.map(function(user){
		return <tr><td>{user.username}</td><td>{user.roles.join(", ")}</td></tr>;
	})
	return rows;
}



  render() {
	  var rows = this.mapData(this.state.data);
    return (
      <div>
        <h2>Admins</h2>
        <p>This message is fetched from the server if you were properly logged in</p>
		<table className="table table-striped table-bordered table-hover ">
		<thead>
			<tr><th>Username</th><th>Roles</th></tr>
		</thead>
		<tbody>
			{rows}
		</tbody>		
		</table>
        {this.state.err && (
          <div className="alert alert-danger errmsg-left" role="alert">
            {this.state.err}
          </div>
		)}
		<div className="allUsers">
		{this.state.users}
		</div>
      </div>
    );
  }
}

export default AdminPage;