import React from 'react';
import FilterButton from '../FilterButton/FilterButton';

function FilterAside({ filters, currentFilter, setFilterCallback }) {
    return (
        <div className="d-flex justify-content-start flex-column mx-0">
            {filters.map((value) => {
                return (
                    <FilterButton
                        filter={value.filter}
                        key={value.filter}
                        currentFilter={currentFilter}
                        onClick={setFilterCallback}
                    >
                        {value.text}
                    </FilterButton>
                );
            })}
        </div>
    );
}

export default FilterAside;