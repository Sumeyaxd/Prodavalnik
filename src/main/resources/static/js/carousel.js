var myCarousel = document.querySelector('#myCarousel');

if (myCarousel) {
    var carousel = new bootstrap.Carousel(myCarousel, {
        interval: 5000,
        wrap: false
    });
}

class xt {
    constructor(options) {
        this.options = options;
        this._addEventListeners();
    }

    _addEventListeners() {
        if (!this.options || !this.options.keyboard) {
            console.error("Опцията 'keyboard' не е дефинирана.");
            return;
        }
        // Съществуващ код за добавяне на слушатели за събития
        console.log('Добавяне на слушатели за събития');
    }
}

// Уверете се, че опциите са дефинирани и предадени правилно
document.addEventListener('DOMContentLoaded', () => {
    const options = {
        keyboard: true, // Уверете се, че това е дефинирано
        // Други опции
    };
    new xt(options);
});
