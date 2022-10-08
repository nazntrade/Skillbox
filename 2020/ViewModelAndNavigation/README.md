# ViewModelAndNavigation

Task
Цель задания
Попрактиковаться в реализации рекомендуемой архитектуры в приложении и использовании библиотек ViewModel, LiveData, Jetpack navigation.



Что нужно сделать
Используйте проект со списком сущностей.
Переделайте проект, чтобы он подходил под рассмотренную архитектуру. Для этого разбейте код на сущности Fragment, ViewModel, Repository.
Убедитесь, что во фрагменте находится код для работы с пользовательским интерфейсом, а в поле фрагмента хранится ViewModel.
Убедитесь, что ViewModel ответственна за хранение состояния экрана и не имеет доступа к пользовательскому интерфейсу, ничего не знает о фрагменте, имеет доступ к репозиторию.
Проверьте, что репозиторий позволяет работать с данными в приложении, не зависит от экрана и от ViewModel, ничего не знает о состоянии экрана.
Убедитесь, что ViewModel хранит внутри себя поле со списком сущностей, которые в настоящий момент должны быть отображены на экране. Используйте для этого LiveData. Убедитесь, что фрагмент подписывается на изменение списка сущностей и в тот момент, как ViewModel обновляет список, фрагмент обновляет его в адаптере.
Сделайте так, чтобы при удалении элемента из списка отображался тост, который оповещает пользователя, что элемент удалён. Оповещать фрагмент о том, что необходимо отобразить тост, должна ViewModel с помощью LiveData.
Подключите Jetpack Navigation в проект, избавьтесь от работы с FragmentManager в проекте.
Создайте экран детальной информации сущности.
Открывайте экран детальной информации сущности по нажатию на элемент списка. Удаление сущности из списка теперь должно происходить по длительному нажатию на элемент списка.


Советы и рекомендации
Для оповещения фрагмента о событиях добавьте в проект класс SingleLiveEvent.
Для обработки длительного нажатия на элемент используйте метод setOnLongClickListener.


Что оценивается
Код оформлен в соответствии с правилами.
Соблюдён принцип инкапсуляции с помощью модификаторов доступа.
Классы являются не финальными (open, abstract) только при необходимости.
Текстовые строки не являются захардкоженными и используются из ресурсов.
Код разнесён по сущностям в соответствии с их ответственностями.
MutableLiveData сокрыт от фрагмента и фрагмент может иметь доступ только к неизменяемой LiveData.
Подписка на LiveData производится с использованием viewLifecycleOwner.
При перевоторах экрана состояние экрана не сбрасывается.
Используется SingleLiveEvent для оповещения об одноразовых событиях.
Если нужно очищать какие-то ресурсы при выходе из экрана — это делается в ViewModel.onCleared.
Вся навигация в проекте сделана с помощью Jetpack Navigation.
В графе навигации у каждого экрана есть превью.


Как отправить задание на проверку
Используйте репозиторий learning_materials / android_basic .
Скачайте изменения в репозитории на локальную машину.
Выполните домашнее задание в папке ViewModelAndNavigation .
Отправьте коммиты в удалённый репозиторий.