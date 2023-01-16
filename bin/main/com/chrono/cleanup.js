const fs = require('fs');

const items = JSON.parse(fs.readFileSync('./monsters-complete.json'));

let newItems = {};

Object.keys(items).map(id => {
    const itemData = items[id];

    if(itemData.duplicate) return;

    const newData = {
        id: itemData.id,
        name: itemData.name,
        release_date: itemData.release_date,
        last_updated: itemData.last_updated
    }

    newItems[id] = newData
});

fs.writeFileSync('./monsters.json', JSON.stringify(newItems));