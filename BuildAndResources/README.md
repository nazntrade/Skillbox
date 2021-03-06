# Сборка и ресурсы

Задача
Цель задания

Попрактиковаться в работе с системой сборки Gradle.
Научиться конфигурировать настройки сборки приложения.
Научиться работать с разными вариантами сборки, типами сборки, flavors.
Научиться настраивать ресурсы для устройств с различными конфигурациями. 

Что нужно сделать

Добавьте два flavors (для платной и бесплатной версии приложения) и настройте три билд-варианта (debug, qa, release).
Для каждого из flavors укажите свою иконку, название и цветовую палитру (используйте sourceSet).
Сделать так, чтобы пользователь мог установить на устройство и бесплатное, и платное приложение (используйте настройку applicationIdSuffix во флейворах).
Настройте отображение параметров сборки (flavor, buildType, versionCode, versionName, applicationId) при открытии приложения на главном экране. Для этого используйте класс BuildConfig.
Сделайте так, чтобы на главном экране изменялся цвет фона в зависимости от ориентации устройства (воспользуйтесь альтернативными ресурсами).
Добавьте локализацию названия приложения для русского и английского языков (воспользуйтесь так же альтернативными ресурсами).
Интегрируйте Git в Android Studio.
* Выведите дерево зависимостей задачи assembleDebug: https://github.com/dorongold/gradle-task-tree. Вывод приложите в файл dependencies.txt в корень проекта. 

Задание со * выполнять необязательно.


Критерии оценивания

В проекте должны быть настроены варианты сборки. 
Каждая сборка должна отличаться ресурсами и конфигурацией в соответствии с заданием.
Цвет фона на главном экране и локализация изменяются с помощью альтернативных ресурсов.
Проект можно собрать и установить на устройство.
Сборки с различными flavors могут быть установлены на устройство параллельно.
Формат сдачи
Перейти на GitLab