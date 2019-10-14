import React from "react";
import { render } from "react-dom";

import {AgGridReact} from 'ag-grid-react';

import 'ag-grid/dist/styles/ag-grid.css';
import "ag-grid-enterprise";
import '../App.css';
import 'ag-grid/dist/styles/ag-theme-balham.css';

import ChildMessageRenderer from "./childMessageRenderer.jsx";
import UploadRenderer from "./uploadRenderer.jsx";
import HistoryRenderer from "./historyRenderer.jsx";

let feedname;
const styles = {
  fontFamily: "sans-serif",
  textAlign: "center"
};


class Main extends React.Component {

  
  state = {
    open: false
  };


  constructor(props) {
    super(props);
    
    this.state = {
            profiles: [],
        	columnDefs: [
				{headerName: "DATAFEEDNAMES", field: "description", cellRenderer: "agGroupCellRenderer"},
				{headerName: "ENTITY", field: "entity"},
                {headerName: "PORTFOLIO", field: "portfolio"},
                {headerName: "ACTIVE_IND", field: "active_ind"},
                // {headerName: 'DOWNLOAD_TEMPLATE', cellRenderer: childMessageRenderer},
                {headerName: 'DOWNLOAD_TEMPLATE', cellRenderer: "childMessageRenderer", colId: "params"},
                {headerName: 'UPLOAD', cellRenderer: "uploadRenderer", colId: "params"},
                {headerName: 'HISTORY', cellRenderer: "historyRenderer", colId: "params"}

      ],
            rowSelection: "single",
			rowData: [],
      frameworkComponents: {
        childMessageRenderer: ChildMessageRenderer,
        uploadRenderer: UploadRenderer,
        historyRenderer:HistoryRenderer
      },
    }
    
  }
  
  async componentDidMount() {
    const response = await fetch('/UX/profiles')
    .then(result => result.json())
    .then(rowData => this.setState({rowData}))
  }
  
  onChange = (e) => {
    const state = this.state
    state[e.target.name] = e.target.value;
    this.setState(state);
  }
  
 
  onGridReady(params) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    const updateData = data => {
      this.setState({ rowData: data });
    };
  }

   onSelectionChanged(value) {
    var selectedRows = this.gridApi.getSelectedRows();
    var selectedRowsString = "";
    
    selectedRows.forEach(function(selectedRow, index) {
      if (index !== 0) {
        selectedRowsString += ", ";
      }
      selectedRowsString += selectedRow.description;
      feedname = selectedRowsString;
      alert("feedname in main.js.is.."+feedname);
    });
    
  }



  render() {
    const { feedname,open} = this.state;
    const {profiles, isLoading} = this.state;
    
    return (
      <div style={styles}>
        <h2>App</h2>

        <div className="App">
        <div className="App-intro">
          <h2>Profile information</h2>
          {profiles.map(profile =>
            <div key={profile.description}>
              {profile.description}
            </div>
          )}
        </div>
      </div>
            <div
				className="ag-theme-balham"
				style={{
					height: '400px',
					width: '100'
				}}
			>
				<AgGridReact
                    enableSorting={true}
                    enableFilter={true}
                    columnDefs={this.state.columnDefs}
                    frameworkComponents={this.state.frameworkComponents}
                    rowSelection={this.state.rowSelection}
                    onGridReady={this.onGridReady.bind(this)}
                    onSelectionChanged={this.onSelectionChanged.bind(this)}
                    rowData={this.state.rowData}
                    
                    >
				</AgGridReact>
			</div>
      </div>
    );
  }
  
}
export {feedname};
export default Main;
