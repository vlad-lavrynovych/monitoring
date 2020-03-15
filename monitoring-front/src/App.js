import React, {useEffect, useState} from 'react';
import logo from './logo.svg';
import './App.css';
import './css/bootstrap.css';
import CheckResultRow from "./components/CheckResultRow";
import AddingForm from "./components/AddingForm";

const App = () => {

    const [results, setResults] = useState([]);

    const updateState = () => {
        console.log("called update state");
        fetch('http://localhost:8081/getAll',
            {
                mode: 'cors',
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                }
            })
            .then((response) => response.json())
            .then(json => setResults(json))
    };

    // setInterval(updateState, 15000);

    useEffect(() => {
        updateState();
        const interval = setInterval(updateState, 15000)
        return () => {
            clearInterval(interval);
        };
    }, []);

    const addConfig = config => {
        fetch('http://localhost:8081/add',
            {
                mode: 'cors',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(config)
            })
            .then((response) => response.json())
            .then(json => setResults(json))
    };

    const deleteResult = id => {
        fetch('http://localhost:8081/delete/' + id,
            {
                mode: 'cors',
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                }
            })
            .then((response) => response.json())
            .then(json => setResults(json))
    };

    const changeMonitoringStatus = id => {
        fetch('http://localhost:8081/update/' + id,
            {
                mode: 'cors',
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                }
            })
            .then((response) => response.json())
            .then(json => setResults(json))
    };
    return (
        <div className="App">
            <div className="navbar navbar-dark bg-dark d-flex justify-content-center align-items-center">
                <h1 className="text-white">Website Monitoring Tool</h1>
            </div>
            <div className="d-flex justify-content-around flex-column">
                <br/>
                <br/>
                <br/>
                <table className="table table-striped table-dark">
                    <thead>
                    <tr>
                        <th>Host</th>
                        <th>Status</th>
                        <th>Last check</th>
                        <th>Duration (millis)</th>
                        <th>Response code</th>
                        <th>Response size (bytes)</th>
                        <th>Details</th>
                        <th>Monitoring status</th>
                    </tr>
                    </thead>
                    <tbody>
                    {results.length > 0 ? (
                        results.map(result => (
                            <CheckResultRow id={result.id} key={result.id} result={result}
                                            deleteResult={deleteResult} changeMonitoringStatus={changeMonitoringStatus}/>))
                    ) : (
                        <tr>
                            <td colSpan={8}>No records yet</td>
                        </tr>
                    )}
                    </tbody>
                </table>
                <br/>
                <br/>
                <br/>

                <AddingForm addConfig={addConfig}/>
            </div>
        </div>
    );
};

export default App;
