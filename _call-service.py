import asyncio
import aiohttp

url = 'http://localhost:8989/append'
num_requests = 5000


async def fetch_data(session, url, counter):
    async with session.post(url, json={'appendWith': counter}) as response:
        await response.json()
        return counter


async def send_requests(url, num_requests):
    async with aiohttp.ClientSession() as session:
        tasks = []
        for counter in range(1, num_requests):
            task = asyncio.ensure_future(fetch_data(session, url, counter))
            tasks.append(task)

        results = await asyncio.gather(*tasks)
        return results


async def main():
    results = await send_requests(url, num_requests)
    print(results)

if __name__ == '__main__':
    loop = asyncio.get_event_loop()
    loop.run_until_complete(main())
