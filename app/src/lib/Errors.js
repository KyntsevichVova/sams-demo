export function errorFor(name, messages = []) {
    return {
        [name]: {
            enabled: messages.length > 0,
            messages: [...messages]
        }
    };
}