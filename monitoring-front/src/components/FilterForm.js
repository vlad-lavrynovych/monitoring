import React, {useState} from "react";

const FilterForm = props => {

    console.log(props);

    const initialState = {
        id: props.id,
        period: 30,
        measure: "seconds"
    };

    const [state, setState] = useState(initialState);

    const changeHandler = (event) => {
        event.persist();
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
                <label className="w-25 text-center" >Period of time</label>
                <input onChange={changeHandler} className="form-control w-25" value={state.period}
                       type="number"
                       name="period"/>
                <select className="custom-select w-25 text-center" name="measure" value={state.measure} onChange={changeHandler}>
                    <option value="seconds">seconds</option>
                    <option value="minutes">minutes</option>
                    <option value="hours">hours</option>
                </select>
                <br/>
                <button className="button btn-outline-danger btn-lg" type="submit" onClick={handleSubmit}>Search
                </button>
            </div>
        </form>
    )
};

export default FilterForm;