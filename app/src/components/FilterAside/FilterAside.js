import React from 'react';
import FilterButton from '../FilterButton/FilterButton';
import { useTranslation } from 'react-i18next';

function FilterAside({ filters, currentFilter, setFilterCallback }) {
    const { t } = useTranslation('table');

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
                        { t(`filter.${value.text}`) }
                    </FilterButton>
                );
            })}
        </div>
    );
}

export default FilterAside;