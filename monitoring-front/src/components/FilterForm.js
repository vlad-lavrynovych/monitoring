import React, {useState} from "react";

const FilterForm = (props) => {

    const {id} = props.id;

    const initialState = {
        id: id,
        period: 30
    };

    const [state, setState] = useState(initialState);

    const changeHandler = (event) => {
        event.persist();
        console.log(event);
        setState(inputs => ({...inputs, [event.target.name]: event.target.value}))
    };

    const handleSubmit = (event) => {
        if (event) {
            event.preventDefault();
            props.filterRequest(state);
            setState(initialState);
        }
    };

    return (
        <form>
            <br/>
            <br/>
            <div className="form-group d-flex flex-md-column justify-content-center align-items-center">
                <label className="w-25 text-center" >Period of time (min)</label>
                <input onChange={changeHandler} className="form-control w-25" value={state.period}
                       type="number"
                       name="period"/>
                <br/>
                <button className="button btn-outline-danger btn-lg" type="submit" onClick={handleSubmit}>Search
                </button>
            </div>
        </form>
    )
};

export default FilterForm;