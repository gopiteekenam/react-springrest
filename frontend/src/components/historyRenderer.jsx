import React, { Component } from "react";
import Modal from "react-responsive-modal";
import { feedname } from './Main.js';
import {AgGridReact} from 'ag-grid-react';

import 'ag-grid/dist/styles/ag-grid.css';
import "ag-grid-enterprise";
import '../App.css';
import 'ag-grid/dist/styles/ag-theme-balham.css';
import HistoryDownloadRenderer from "./historyDownloadRenderer.jsx";


let filename;

export default class historyRenderer extends Component {
  

    state = {
        open: false,
        profileHistory: [],
        	columnDefs: [
				{headerName: "UPLOADVERSION", field: "uploadVersion", cellRenderer: "agGroupCellRenderer"},
				{headerName: "FILENAME", field: "filename"},
                {headerName: 'DOWNLOAD', cellRenderer: "historyDownloadRenderer", colId: "params"},
            ],
            rowSelection: "single",
			rowData: [],
			frameworkComponents: {
        historyDownloadRenderer: HistoryDownloadRenderer
      },
    };

    
    showHistoryData(ev){
    const API ='/UX/profileHistory?feedname=';
    const query = feedname;
    const localfeedname=feedname;
    alert("local feedname:"+localfeedname);
    fetch(API+query)
    .then(result => result.json())
    .then(rowData => 
    {
        console.log("feedname in data:"+feedname);
              console.log("rowdata in data:"+rowData);
    				  if(rowData === '' ||localfeedname !== '' && localfeedname !== feedname){
    				  alert("inside data is empty");
    				  	this.showHistoryData();
    				  }
    				  else{
   
    this.setState({rowData});
    this.onOpenModal();
    }
    })
    }
    
    onOpenModal = () => {
    this.setState({ open: true });    
    };

    onCloseModal = () => {
        this.setState({ open: false });
    };
    
    constructor(props) {
        super(props);
        this.showHistoryData = this.showHistoryData.bind(this);
    }
    
    onGridReady(params) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    data => {
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
      selectedRowsString += selectedRow.filename;
      filename = selectedRowsString;
      alert("filename in historyRenderer.is.."+filename);
    });
    
  }

    

    render() {
    const { filename,open} = this.state;
    const {profileHistory} = this.state;
        return (
        
        <div>
                <span><button style={{ height: 20, lineHeight: 0.5 }} onClick={this.showHistoryData} className="btn btn-info">History</button></span>
       
       <Modal open={open} onClose={this.onCloseModal} center>
                <h2>History data</h2>
             <div
				className="ag-theme-balham"
				style={{
					height: '400px',
					width: '600px'
				}}
			>
				<AgGridReact
                    enableSorting={true}
                    enableFilter={true}
                    columnDefs={this.state.columnDefs}
                    frameworkComponents={this.state.frameworkComponents}
                    rowSelection={this.state.rowSelection}
                    onGridReady={this.onGridReady.bind(this)}
                    rowData={this.state.rowData}
                    onSelectionChanged={this.onSelectionChanged.bind(this)}
                    >
				</AgGridReact>
			</div>   
		</Modal>
		</div>                
    );

    }
};
export {filename};