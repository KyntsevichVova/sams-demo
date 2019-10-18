import React from 'react';
import './FilterButton.css'


function FilterButton(props) {
    return (
        <button
            onClick={() => {props.onClick(props.filter)}}
            className={`filterButton${props.filter === props.currentFilter ? " active" : ""}`}
        >
            {props.children}
        </button>
    );
}

export default FilterButton;