import React, {Component} from "react";
import { filename } from './historyRenderer.jsx';
import { CSVLink, CSVDownload } from "react-csv";



export default class HistoryDownloadRenderer extends Component {
    state = {
        data: ''
     };
    
         constructor(props) {
        super(props);
        this.downloadTemplate = this.downloadTemplate.bind(this);
    }

    downloadTemplate(ev) {
        ev.preventDefault();
        alert("filename in HistoryDownloadRenderer is:"+filename)
        const API ='/UX/historycsv?filename=';
        const query = filename;
        fetch(API+query).then(response => 
    	response.text()
    	.then(data => {
    				  this.setState({ data }, () => {
                      this.link.href = 'data:text/csv;charset=UTF-8,${encodeURI(data)}'
                      this.link.download = filename
                      this.link.click()       	
       })
		
		}));
    }



    render() {
        return (
            <span>
                <div>
                 <a ref={link => this.link = link}
        style={{display: 'none'}}
      />   
                <button style={{height: 20, lineHeight: 0.5}} onClick={this.downloadTemplate} className="btn btn-info">Download</button>
                </div>
                </span>
        );
    }
};