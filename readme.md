~~~~
CategoryTests
    getCategoryPositiveTest
        Категория соответсвует 1
        Тип продукта Food
        Количество найденных продуктов в категории соответсвует действительности
        Статус ответа 200
        Все продукты найденные в категории имеют отдин тип Food        
    getCategoryNegativeTest
        Статус ответа 404
        Ответ пришел errorBody
 
**PostPositiveTests**
        Статус ответа 201
        Id not null

**PostNegativeTests**
        Статус ответа 400

**PutPositiveTests**
        Статус ответа 200
        Не изменилась категория
        Не изменился id
        Не изменился тип товара
        Цена изменилась

**PutNegativeTests**
        Статус ответа 400

**DeletePositiveTests**
        Статус ответа 200
        Проверка что продукт не находится Get запросом        

**DeleteNegativeTests**
        Ответ пришел errorBody
        Статус ответа 204

**GetPositiveTests**
        Статус ответа 200
        Категория верна
        id верен
        Тип товара верен
        Цена соответсвует

**GetNegativeTests**
        Ответ пришел errorBody
        Статус ответа 404        