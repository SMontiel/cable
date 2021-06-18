replaceComponents(document)

function replaceComponents(rootNode) {
    const cableAttachedNodes = rootNode.querySelectorAll("*[wire-attach]")
    cableAttachedNodes.forEach(node => {
        const componentName = node.getAttribute('wire-attach')
        fetch('/cable/' + componentName)
            .then(response => response.text())
            .then(data => {
                const newItem = document.createElement('div');
                newItem.innerHTML = data
                node.parentNode.replaceChild(newItem, node)

                findWithClickAttr(componentName, newItem)
            })
    })
}

function findWithClickAttr(componentName, rootNode) {
    const cableClickNodes = rootNode.querySelectorAll("*[wire-click]")
    cableClickNodes.forEach(clickNode => {
        const route = clickNode.getAttribute('wire-click')
        clickNode.addEventListener("click", () => {
            fetch('/cable/' + componentName + '/' + route)
                .then(response => response.text())
                .then(data => {
                    rootNode.innerHTML = data

                    findWithClickAttr(componentName, rootNode)
                });
        })
    })
}

function findAttachedComponents(rootNode) {
    const cableAttachedNodes = rootNode.querySelectorAll("*[wire-attach]")
    cableAttachedNodes.forEach(node => {
        const componentName = node.getAttribute('wire-attach')
        findWithClickAttr(componentName, node)
    })
}