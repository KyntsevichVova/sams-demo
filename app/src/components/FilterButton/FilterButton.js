import React from 'react';

function FilterButton(props) {
    return (
        <button
            onClick={() => {props.onClick(props.filter)}}
            className={`btn${props.filter === props.currentFilter ? " btn-primary active" : " btn-default"}`}
        >
            {props.children}
        </button>
    );
}

export default FilterButton;